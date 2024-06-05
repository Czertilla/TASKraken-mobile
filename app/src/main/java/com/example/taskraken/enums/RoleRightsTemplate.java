package com.example.taskraken.enums;

public enum RoleRightsTemplate {
    ORDINARY ("ordinary"),
    HEAD("head"),
    GENDIR("dendir"),
    HR("hr"),
    DEFAULT (null);

    private final String type;

    RoleRightsTemplate(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
