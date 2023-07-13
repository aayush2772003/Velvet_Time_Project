package com.oops.project.user.model;

public enum Role {
    USER("User"),
    ADMIN("Admin"),
    CUSTOMER("Customer"),
    MANAGER("Manager");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}