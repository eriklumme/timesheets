package org.erik.timesheets.ui.views.statistics;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.erik.timesheets.MainLayout;
import org.erik.timesheets.domain.service.TimesheetEntryService;
import org.erik.timesheets.ui.views.AbstractView;

import java.time.Instant;
import java.time.ZonedDateTime;

@Route(value = "statistics", layout = MainLayout.class)
@PageTitle("Statistics")
//@CssImport("styles/statistics.css")
public class StatisticsView extends AbstractView {

    private final TimesheetEntryService timesheetEntryService;

    public StatisticsView(TimesheetEntryService timesheetEntryService) {
        this.timesheetEntryService = timesheetEntryService;
    }

    @Override
    protected void onAttachOnce(AttachEvent attachEvent) {
        ZonedDateTime startDateTime = ZonedDateTime.now();
        startDateTime = startDateTime.minusDays(startDateTime.getDayOfWeek().getValue() - 1);
        startDateTime = startDateTime.withHour(0).withMinute(0).withSecond(0).withNano(0);
        ZonedDateTime endDateTime = startDateTime.plusDays(7);

        double currentWeekHours = timesheetEntryService.getCurrentUserHoursInPeriod(
                Instant.from(startDateTime), Instant.from(endDateTime));
        double lastWeekHours = timesheetEntryService.getCurrentUserHoursInPeriod(
                Instant.from(startDateTime.minusDays(7)), Instant.from(startDateTime));

        Span currentWeekSpan = new Span(String.format("Your hours in the current week: %.1f", currentWeekHours));
        Span lastWeekSpan = new Span(String.format("Your hours in the previous week: %.1f", lastWeekHours));
        add(currentWeekSpan, lastWeekSpan);
    }
}
