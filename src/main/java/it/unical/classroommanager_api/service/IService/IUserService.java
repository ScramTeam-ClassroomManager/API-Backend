package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;

import java.util.List;

public interface IUserService {
    UserDto findBySerialNumber(Integer serialNumber);
    UserDto registerUser(RegisterDto registerDto);
    List<UserDto> getAllUsers();
    void deleteUser(Integer serialNumber);
}

