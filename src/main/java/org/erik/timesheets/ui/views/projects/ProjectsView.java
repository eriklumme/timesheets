package org.erik.timesheets.ui.views.projects;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.erik.timesheets.MainLayout;
import org.erik.timesheets.domain.dto.ClientDTO;
import org.erik.timesheets.domain.service.ClientService;
import org.erik.timesheets.domain.service.ProjectService;
import org.erik.timesheets.domain.service.TimesheetEntryService;
import org.erik.timesheets.ui.views.AbstractView;

@Route(value = "projects", layout = MainLayout.class)
@PageTitle("Projects")
public class ProjectsView extends AbstractView {

    private final ClientService clientService;
    private final ProjectService projectService;
    private final TimesheetEntryService timesheetEntryService;

    public ProjectsView(ClientService clientService, ProjectService projectService,
                        TimesheetEntryService timesheetEntryService) {
        this.clientService = clientService;
        this.projectService = projectService;
        this.timesheetEntryService = timesheetEntryService;
    }

    @Override
    protected void onAttachOnce(AttachEvent attachEvent) {
        ComboBox<ClientDTO> clientCombo = new ComboBox<>();
        clientCombo.setItems(clientService.getAllClients());
        clientCombo.setItemLabelGenerator(ClientDTO::getName);
        clientCombo.setPlaceholder("Choose a client");

        ProjectGrid projectGrid = new ProjectGrid(timesheetEntryService);
        clientCombo.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                projectGrid.setItems(projectService.getByClient(e.getValue()));
            }
        });

        add(clientCombo, projectGrid);
    }
}
