package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.entities.User;
import it.unical.classroommanager_api.repository.UserRepository;
import it.unical.classroommanager_api.service.IService.IUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto findBySerialNumber(Integer serialNumber) {
        User user = userRepository.findBySerialNumber(serialNumber);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto registerUser(RegisterDto registerDto) {
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}

