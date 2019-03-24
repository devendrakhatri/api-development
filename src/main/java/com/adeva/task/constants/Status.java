package com.adeva.task.constants;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Constant for API call's status.
 */
public enum Status {

    SUCCESS("success"),
    FAILURE("failure");

    private String name;

    Status(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
