package it.unical.classroommanager_api.dto;

import it.unical.classroommanager_api.enums.ClassroomType;
import lombok.Data;

@Data
public class ClassroomDto {
    private long id;
    private String name;
    private int cubeNumber;
    private int floor;
    private int capability;
    private int numSocket;
    private boolean projector;
    private boolean available;
    private ClassroomType type;
}

