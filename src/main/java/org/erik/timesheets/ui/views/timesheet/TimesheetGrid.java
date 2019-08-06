package org.erik.timesheets.ui.views.timesheet;

import com.vaadin.flow.data.renderer.TemplateRenderer;
import org.erik.timesheets.AbstractGrid;
import org.erik.timesheets.domain.dto.TimesheetEntryDTO;

public class TimesheetGrid extends AbstractGrid<TimesheetEntryDTO> {

    private static final String TEMPLATE =
            "<div class=timesheet-grid__entry>" +
                    "<div class=timesheet-grid__entry-header>" +
                        "<span class=timesheet-grid__entry-client>[[item.client]]</span>" +
                        "<span class=timesheet-grid__entry-project>[[item.project]]</span>" +
                    "</div>" +
                    "<div class=timesheet-grid__entry-comment>[[item.comment]]</div>" +
                    "<div class=timesheet-grid__entry-time>" +
                        "<span class=timesheet-grid__entry-duration>[[item.duration]]</span>" +
                        "<span class=timesheet-grid__entry-timestamp>[[item.timestamp]]</span>" +
                    "</div>" +
            "</div>";

    public TimesheetGrid() {
        addColumn(TemplateRenderer.<TimesheetEntryDTO>of(TEMPLATE)
            .withProperty("client", entry -> entry.getClient().getName())
            .withProperty("project", entry -> entry.getProject().getName())
            .withProperty("comment", TimesheetEntryDTO::getComment)
            .withProperty("timestamp", TimesheetEntryDTO::getFormattedTimestamp)
            .withProperty("duration", TimesheetEntryDTO::getFormattedDuration));
    }
}
