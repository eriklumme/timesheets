package org.erik.timesheets.domain.service;

import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.erik.timesheets.domain.dto.ClientDTO;
import org.erik.timesheets.domain.dto.ProjectDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@VaadinSessionScope
public class ProjectService {

    private static final Faker faker = new Faker();

    private static final Random random = new Random();

    private final List<ProjectDTO> allProjects;

    public ProjectService(ClientService clientService) {
        List<ClientDTO> allClients = clientService.getAllClients();

        allProjects = IntStream.range(0, 40).mapToObj(i ->
                new ProjectDTO(allClients.get(random.nextInt(allClients.size())),
                        String.join(" ", faker.words(2))))
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getAllProjects() {
        return allProjects.stream().sorted(Comparator.comparing(ProjectDTO::getName))
                .collect(Collectors.toList());
    }

    public List<ProjectDTO> getByClient(ClientDTO client) {
        return allProjects.stream().filter(project -> client.equals(project.getClient()))
                .collect(Collectors.toList());
    }
}
