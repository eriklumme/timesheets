package org.erik.timesheets.domain.dto;

import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

public class UserDTO {

    private String firstName;
    private String lastName;

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        Assert.notNull(firstName, "First name may not be null");
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        Assert.notNull(firstName, "Last name may not be null");
        this.lastName = lastName;
    }

    public UserDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
