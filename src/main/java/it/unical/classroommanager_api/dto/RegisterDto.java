package it.unical.classroommanager_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {
    @NotNull(message = "serialNumber.not.empty")
    @Pattern(regexp = "^[0-9]{6}$", message = "serialNumber.invalid")
    private Integer serialNumber;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "email.not.empty")
    @Email(message = "email.not.valid")
    private String email;
    @NotEmpty(message = "password.not.empty")
    private String password;
}