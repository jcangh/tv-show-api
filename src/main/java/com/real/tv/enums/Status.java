package com.real.tv.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Status enum to be used in entities or dto
 */
public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String value;

    Status(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
