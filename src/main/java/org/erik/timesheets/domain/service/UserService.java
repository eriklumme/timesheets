package org.erik.timesheets.domain.service;

import com.github.javafaker.Faker;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.erik.timesheets.domain.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@VaadinSessionScope
public class UserService {

    private final static Faker faker = new Faker();
    private final static List<UserDTO> allUsers;

    static {
        allUsers = IntStream.range(0, 50)
                .mapToObj(i -> new UserDTO(faker.firstName(), faker.lastName()))
                .collect(Collectors.toList());
    }

    public List<UserDTO> getAllUsers() {
        return allUsers;
    }

    public UserDTO getCurrentUser() {
        return allUsers.get(0);
    }
}
