package it.unical.classroommanager_api.dto;

import it.unical.classroommanager_api.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull(message = "serialNumber.not.empty")
    private Integer serialNumber;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "email.not.empty")
    @Email(message = "email.not.valid")
    private String email;
    @NotEmpty(message = "password.not.empty")
    private String password;
    @NotEmpty(message = "role.not.empty")
    private Role role;
}