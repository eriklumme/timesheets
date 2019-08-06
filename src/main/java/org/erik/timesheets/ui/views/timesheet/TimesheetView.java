package org.erik.timesheets.ui.views.timesheet;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import org.erik.timesheets.MainLayout;
import org.erik.timesheets.domain.service.TimesheetEntryService;
import org.erik.timesheets.domain.service.UserService;
import org.erik.timesheets.ui.views.AbstractView;
import org.springframework.context.ApplicationContext;

@Route(value = "timesheet", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Timesheet")
@CssImport("styles/timesheet.css")
public class TimesheetView extends AbstractView {

    private final TimesheetEntryService timesheetEntryService;
    private final UserService userService;
    private final ApplicationContext applicationContext;

    public TimesheetView(TimesheetEntryService timesheetEntryService, UserService userService,
                         ApplicationContext applicationContext) {
        super();
        this.timesheetEntryService = timesheetEntryService;
        this.userService = userService;
        this.applicationContext = applicationContext;
    }

    @Override
    protected void onAttachOnce(AttachEvent attachEvent) {
        TimesheetGrid timesheetGrid = new TimesheetGrid();
        timesheetGrid.setItems(
                timesheetEntryService.getAllTimesheetEntriesForUser(userService.getCurrentUser()));

        Div toolbar = new Div();
        toolbar.getStyle().set("display", "flex");

        Button newEntryButton = new Button("Add entry", VaadinIcon.PENCIL.create());
        newEntryButton.getStyle().set("margin-left", "auto");
        newEntryButton.addClickListener(e -> {
            TimesheetEntryDialog timesheetEntryDialog = applicationContext.getBean(TimesheetEntryDialog.class);
            timesheetEntryDialog.setOnCreateListener(entry -> {
                timesheetEntryService.save(entry);
                timesheetGrid.setItems(
                        timesheetEntryService.getAllTimesheetEntriesForUser(userService.getCurrentUser()));
                Notification.show("Entry created");
            });
            timesheetEntryDialog.open();
        });

        add(timesheetGrid, newEntryButton);
    }
}
