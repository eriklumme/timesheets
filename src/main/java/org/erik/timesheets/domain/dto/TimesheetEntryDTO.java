package org.erik.timesheets.domain.dto;

import org.erik.timesheets.i18n.I18NProvider;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimesheetEntryDTO {

    private static final int SECONDS_IN_HOUR = 3600;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofLocalizedTime(FormatStyle.SHORT)
            .withLocale(I18NProvider.getLocale());

    private UserDTO user;
    private ProjectDTO project;
    private ClientDTO client;
    private String comment;
    private Instant startTime;
    private Instant endTime;

    public TimesheetEntryDTO() {}

    public TimesheetEntryDTO(UserDTO user, ProjectDTO project, ClientDTO client,
                             String comment, Instant startTime, Instant endTime) {
        this.user = user;
        this.project = project;
        this.client = client;
        this.comment = comment;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFormattedTimestamp() {
        return dateTimeFormatter.format(startTime.atZone(ZoneId.systemDefault())) + " - " +
                dateTimeFormatter.format(endTime.atZone(ZoneId.systemDefault()));
    }

    public String getFormattedDuration() {
        Duration duration = Duration.between(startTime, endTime);
        StringBuilder outputSB = new StringBuilder();

        long hours = duration.getSeconds() / SECONDS_IN_HOUR;
        if (hours > 0) {
            outputSB.append(hours + "h");
        }
        long remindingSecondsAfterHours = duration.getSeconds() % SECONDS_IN_HOUR;
        if (remindingSecondsAfterHours != 0) {
            long minutes = remindingSecondsAfterHours / 60;
            outputSB.append(" " + minutes + "m");

            long remindingSecondsAfterMinutes = remindingSecondsAfterHours % 60;
            if (remindingSecondsAfterMinutes != 0) {
                outputSB.append(" " + remindingSecondsAfterMinutes + "s");
            }
        }

        return outputSB.toString();
    }
}
