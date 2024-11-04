package it.unical.classroommanager_api.dto;

import lombok.Data;

@Data
public class ClassroomDto {

    private long id;
    private String name;
    private int cube;
    private int floor;
    private int capability;
    private int numSocket;
    private boolean projector;
}
