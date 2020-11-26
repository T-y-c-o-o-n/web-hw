package ru.itmo.wp.form;

import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Component
public class DisableEnableCredentials {
    @NotNull
    @NotEmpty
    private String idString;

    @NotNull
    @Pattern(regexp = "Disable|Enable", message = "Expected action \"Disable\" or \"Enable\"")
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
}
