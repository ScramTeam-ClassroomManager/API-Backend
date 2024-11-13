package it.unical.classroommanager_api.enums;

public enum ClassroomType {
    NORMAL("NORMAL"),
    AUDITORIUM("AUDITORIUM");

    private final String type;

    ClassroomType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}