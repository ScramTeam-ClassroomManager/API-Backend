package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.configuration.i18n.MessageLang;
import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.entities.User;
import it.unical.classroommanager_api.repository.UserRepository;
import it.unical.classroommanager_api.service.IService.IUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageLang messageLang;

    @Override
    public UserDto findBySerialNumber(Integer serialNumber) {
        Optional<User> user = Optional.ofNullable(userRepository.findBySerialNumber(serialNumber))
                .orElseThrow(() -> new EntityNotFoundException(messageLang.getMessage("user.not.found")));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto registerUser(RegisterDto registerDto) {
        // Check if email already exists
        if (userRepository.existsBySerialNumberOrEmail(registerDto.getSerialNumber(),registerDto.getEmail())) {
            throw new EntityExistsException(messageLang.getMessage("serialNumber.email.duplicate"));
        }
        User user = modelMapper.map(registerDto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}

