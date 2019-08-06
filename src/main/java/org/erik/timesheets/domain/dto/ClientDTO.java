package org.erik.timesheets.domain.dto;

public class ClientDTO {

    private String name;

    public ClientDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
