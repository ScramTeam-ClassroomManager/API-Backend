package it.unical.classroommanager_api.dto;

import it.unical.classroommanager_api.enums.Status;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RequestDto {

    private Long id;
    private String reason;
    private long classroomId;
    private int userSerialNumber;
    private LocalDate creationDate;
    private Status status;
    private LocalTime startHour;
    private LocalTime endHour;
    private LocalDate requestDate;
    private String adminResponse;
}
