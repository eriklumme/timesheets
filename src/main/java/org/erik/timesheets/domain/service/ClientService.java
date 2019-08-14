package org.erik.timesheets.domain.service;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.erik.timesheets.domain.dto.ClientDTO;
import org.erik.timesheets.utils.FakerUtils;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@VaadinSessionScope
public class ClientService {

    private final static List<ClientDTO> allClients;

    static {
        allClients = IntStream.range(0, 20).mapToObj(i ->
                new ClientDTO(FakerUtils.getClient()))
            .collect(Collectors.toList());
    }

    public List<ClientDTO> getAllClients() {
        return allClients.stream().sorted(Comparator.comparing(ClientDTO::getName))
                .collect(Collectors.toList());
    }
}
