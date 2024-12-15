package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.configuration.i18n.MessageLang;
import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.LoginDto;
import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.security.CustomUserDetailsService;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.IService.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.AUTHPATH)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageLang messageLang;

    @PostMapping(value = APIConstant.LOGIN)
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto) {
        authenticate(Integer.parseInt(loginDto.getSerialNumber()), loginDto.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(loginDto.getSerialNumber()));
        String token = jwtService.generateToken(userDetails);
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }

    @PostMapping(value = APIConstant.REGISTER)
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto) {
        UserDto createdUser = userService.registerUser(registerDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping(APIConstant.GETCOMPLETENAMEUSER + "/{serialNumber}")
    public ResponseEntity<Map<String, String>> getUserNameBySerialNumber(@PathVariable int serialNumber) {
        UserDto userDto = userService.findBySerialNumber(serialNumber);
        Map<String, String> userInfo = Map.of(
                "firstName", userDto.getFirstName(),
                "lastName", userDto.getLastName()
        );
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    @GetMapping(value = APIConstant.ALLUSERS)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping(value = APIConstant.DELETEUSER + "/{serialNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable int serialNumber) {
        userService.deleteUser(serialNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void authenticate(int serialNumber, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(serialNumber, password);
        try {
            this.authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new RuntimeException(messageLang.getMessage("credentials.not.valid"));
        }
    }
}

