package it.unical.classroommanager_api.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String serialNumber;
    private String password;
}
