package it.unical.classroommanager_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDto {

    private Long id;
    private String reason;
    private long classroomId;
    private int userSerialNumber;
    private LocalDate creationDate;
}

