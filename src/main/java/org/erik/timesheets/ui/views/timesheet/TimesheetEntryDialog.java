package org.erik.timesheets.ui.views.timesheet;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.erik.timesheets.domain.dto.ClientDTO;
import org.erik.timesheets.domain.dto.ProjectDTO;
import org.erik.timesheets.domain.dto.TimesheetEntryDTO;
import org.erik.timesheets.domain.service.ClientService;
import org.erik.timesheets.domain.service.ProjectService;
import org.erik.timesheets.domain.service.UserService;
import org.erik.timesheets.utils.TimeUtils;
import org.springframework.context.annotation.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.function.Consumer;

import static org.erik.timesheets.utils.TimeUtils.roundToNearestMinutes;

@SpringComponent
@Scope("prototype")
public class TimesheetEntryDialog extends Dialog {

    private final ClientService clientService;
    private final ProjectService projectService;
    private final UserService userService;

    private Consumer<TimesheetEntryDTO> onCreateListener;

    public TimesheetEntryDialog(ClientService clientService, ProjectService projectService,
                                UserService userService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        if (attachEvent.isInitialAttach()) {
            init();
        }
    }

    private void init() {
        Binder<TimesheetEntryDTO> binder = new Binder<>();

        ComboBox<ClientDTO> clientCombo = new ComboBox<>("Client");
        clientCombo.setItems(clientService.getAllClients());
        clientCombo.setItemLabelGenerator(ClientDTO::getName);
        binder.forField(clientCombo)
                .asRequired()
                .bind(TimesheetEntryDTO::getClient, TimesheetEntryDTO::setClient);

        ComboBox<ProjectDTO> projectCombo = new ComboBox<>("Project");
        projectCombo.setItems(projectService.getAllProjects());
        projectCombo.setItemLabelGenerator(ProjectDTO::getName);
        binder.forField(projectCombo)
                .asRequired()
                .bind(TimesheetEntryDTO::getProject, TimesheetEntryDTO::setProject);

        clientCombo.addValueChangeListener(e -> {
            if (!e.isFromClient()) {
                return;
            }
            if (e.getValue() != null) {
                projectCombo.setItems(projectService.getByClient(e.getValue()));
            } else {
                projectCombo.setItems(projectService.getAllProjects());
            }
        });

        projectCombo.addValueChangeListener(e -> {
            ClientDTO projectClient = e.getValue() != null ?
                    e.getValue().getClient() : null;
            if (projectClient != null && !projectClient.equals(clientCombo.getValue())) {
                clientCombo.setValue(e.getValue().getClient());
            }
        });

        TextArea descriptionField = new TextArea("Description");
        descriptionField.setPlaceholder("What have you been up to?");

        TimePicker startTimePicker = new TimePicker("Start time");
        startTimePicker.setStep(Duration.ofMinutes(15));
        startTimePicker.setValue(roundToNearestMinutes(LocalTime.now(), 15));
        binder.forField(startTimePicker)
                .asRequired()
                .bind(this::getStartTime, this::setStartTime);

        TimePicker endTimePicker = new TimePicker("End time");
        endTimePicker.setStep(Duration.ofMinutes(15));
        endTimePicker.setValue(roundToNearestMinutes(LocalTime.now().plusMinutes(60), 15));
        binder.forField(endTimePicker)
                .asRequired()
                .bind(this::getEndTime, this::setEndTime);

        Button cancelButton = new Button("Cancel", VaadinIcon.CLOSE.create());
        cancelButton.addClickListener(e -> close());

        Button confirmButton = new Button("Create", VaadinIcon.CHECK.create());
        confirmButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        confirmButton.addClickShortcut(Key.ENTER);
        confirmButton.addClickListener(e -> {
            if (onCreateListener != null && binder.validate().isOk()) {
                TimesheetEntryDTO timesheetEntry = binder.getBean();
                timesheetEntry.setUser(userService.getCurrentUser());
                onCreateListener.accept(timesheetEntry);
                close();
            } else {
                Notification notification = new Notification(
                        "There are errors in the time entry",
                        3000,
                        Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
            }
        });

        binder.withValidator((dto, context) -> {
            if (dto.getStartTime() == null || dto.getEndTime() == null ||
                    dto.getStartTime().isBefore(dto.getEndTime())) {
                return ValidationResult.ok();
            }
            return ValidationResult.error("Start time must be before end time");
        });
        binder.setBean(new TimesheetEntryDTO());

        HorizontalLayout toolbar = new HorizontalLayout(cancelButton, confirmButton);
        toolbar.getStyle().set("justify-content", "space-between");
        toolbar.getStyle().set("margin-top", "var(--lumo-space-m)");

        VerticalLayout content = new VerticalLayout(
                clientCombo, projectCombo,
                descriptionField,
                startTimePicker, endTimePicker,
                toolbar
        );
        content.setSpacing(false);

        add(content);

        setCloseOnOutsideClick(false);
        setCloseOnEsc(true);
    }

    private LocalTime getStartTime(TimesheetEntryDTO timesheetEntry) {
        return timesheetEntry.getStartTime() == null ? null :
                LocalTime.from(timesheetEntry.getStartTime());
    }

    private void setStartTime(TimesheetEntryDTO timesheetEntry, LocalTime localTime) {
        timesheetEntry.setStartTime(localTime == null ? null : TimeUtils.fromUserTime(localTime));
    }

    private LocalTime getEndTime(TimesheetEntryDTO timesheetEntry) {
        return timesheetEntry.getEndTime() == null ? null :
                LocalTime.from(timesheetEntry.getEndTime());
    }

    private void setEndTime(TimesheetEntryDTO timesheetEntry, LocalTime localTime) {
        timesheetEntry.setEndTime(localTime == null ? null : TimeUtils.fromUserTime(localTime));
    }

    /**
     * Sets a consumer that is called with the (unsaved) timesheet entry
     * when it has been created.
     */
    public void setOnCreateListener(Consumer<TimesheetEntryDTO> onCreateListener) {
        this.onCreateListener = onCreateListener;
    }
}
