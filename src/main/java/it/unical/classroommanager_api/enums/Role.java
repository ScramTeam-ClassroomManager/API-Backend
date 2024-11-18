package it.unical.classroommanager_api.enums;

public enum Role {
    PROFESSOR("PROFESSOR"),
    ASSISTANT("ASSISTANT"),
    ADMIN("ADMIN");


    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRoleName() {
        return role;
    }
}
