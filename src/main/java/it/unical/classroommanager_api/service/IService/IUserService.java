package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.entities.User;

public interface IUserService {
    UserDto findBySerialNumber(Integer serialNumber);
    UserDto registerUser(RegisterDto registerDto);
}

