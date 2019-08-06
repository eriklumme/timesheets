package org.erik.timesheets.ui.views.projects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.erik.timesheets.domain.dto.ProjectDTO;
import org.erik.timesheets.domain.service.TimesheetEntryService;

public class ProjectGrid extends Grid<ProjectDTO> {

    private final TimesheetEntryService timesheetEntryService;

    public ProjectGrid(TimesheetEntryService timesheetEntryService) {
        this.timesheetEntryService = timesheetEntryService;

        addColumn(ProjectDTO::getName).setHeader("Project name");
        setItemDetailsRenderer(new ComponentRenderer<>(this::createDetailsComponent));
    }

    private Component createDetailsComponent(ProjectDTO project) {
        VerticalLayout content = new VerticalLayout();
        content.add(new Span(String.format("Hours spent: %.2f",
                timesheetEntryService.getHoursOnProject(project))));
        return content;
    }
}
