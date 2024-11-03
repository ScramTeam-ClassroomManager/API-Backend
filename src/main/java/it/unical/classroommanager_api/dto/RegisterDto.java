package it.unical.classroommanager_api.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private Integer serialNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}