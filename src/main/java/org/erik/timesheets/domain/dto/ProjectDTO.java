package org.erik.timesheets.domain.dto;

import javax.validation.constraints.NotNull;

public class ProjectDTO {

    private ClientDTO client;
    private String name;

    public ProjectDTO(@NotNull ClientDTO client, @NotNull String name) {
        this.client = client;
        this.name = name;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public ClientDTO getClient() {
        return client;
    }

    public void setClient(@NotNull ClientDTO client) {
        this.client = client;
    }
}
