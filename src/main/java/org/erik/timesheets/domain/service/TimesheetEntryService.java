package org.erik.timesheets.domain.service;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.erik.timesheets.domain.dto.ClientDTO;
import org.erik.timesheets.domain.dto.ProjectDTO;
import org.erik.timesheets.domain.dto.TimesheetEntryDTO;
import org.erik.timesheets.domain.dto.UserDTO;
import org.erik.timesheets.utils.FakerUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@VaadinSessionScope
public class TimesheetEntryService {

    private static final Random random = new Random();

    private final UserService userService;
    private final ProjectService projectService;
    private final ClientService clientService;

    private List<TimesheetEntryDTO> allTimesheetEntries;

    public TimesheetEntryService(UserService userService, ProjectService projectService, ClientService clientService) {
        this.userService = userService;
        this.projectService = projectService;
        this.clientService = clientService;

        allTimesheetEntries = IntStream.range(0, 200).mapToObj(i -> {
            Instant startTime = ZonedDateTime.now()
                    .minusDays(random.nextInt(30))
                    .withHour(8 + random.nextInt(12))
                    .withMinute(random.nextInt(12) * 5)
                    .withSecond(0).withNano(0).toInstant();
            Instant endTime = startTime.plusSeconds(60 * random.nextInt(12 * 8) * 5);
            List<UserDTO> allUsers = userService.getAllUsers();
            List<ClientDTO> allClients = clientService.getAllClients();
            List<ProjectDTO> allProejcts = projectService.getAllProjects();
            return new TimesheetEntryDTO(
                    allUsers.get(random.nextInt(allUsers.size())),
                    allProejcts.get(random.nextInt(allProejcts.size())),
                    allClients.get(random.nextInt(allClients.size())),
                    FakerUtils.getComment(),
                    startTime, endTime
            );
        }).collect(Collectors.toList());
    }

    private double getHoursInEntry(TimesheetEntryDTO entry) {
        return Duration.between(entry.getStartTime(), entry.getEndTime())
                .toMinutes() / 60.0;
    }

    public List<TimesheetEntryDTO> getAllTimesheetEntries() {
        return allTimesheetEntries;
    }

    public List<TimesheetEntryDTO> getAllTimesheetEntriesForUser(UserDTO userDTO) {
        return allTimesheetEntries.stream()
                .filter(entry -> userDTO.equals(entry.getUser()))
                .sorted(Comparator.comparing(TimesheetEntryDTO::getStartTime))
                .collect(Collectors.toList());
    }

    public double getCurrentUserHoursInPeriod(Instant fromInstant, Instant toInstant) {
        return getAllTimesheetEntriesForUser(userService.getCurrentUser()).stream()
                .filter(entry -> entry.getStartTime().isAfter(fromInstant) &&
                        entry.getEndTime().isBefore(toInstant))
                .mapToDouble(this::getHoursInEntry)
                .sum();
    }

    public double getHoursOnProject(ProjectDTO project) {
        return allTimesheetEntries.stream()
                .filter(entry -> project.equals(entry.getProject()))
                .mapToDouble(this::getHoursInEntry)
                .sum();
    }

    public void save(TimesheetEntryDTO timesheetEntry) {
        allTimesheetEntries.add(timesheetEntry);
    }
}
