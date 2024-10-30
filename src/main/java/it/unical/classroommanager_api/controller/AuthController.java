package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.LoginDto;
import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.security.CustomUserDetailsService;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.IService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = APIConstant.LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        authenticate(Integer.parseInt(loginDto.getSerialNumber()), loginDto.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(loginDto.getSerialNumber()));
        String token = jwtService.generateToken(userDetails);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterDto registerDto) {
        UserDto createdUser = userService.registerUser(registerDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    private void authenticate(int serialNumber, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(serialNumber, password);
        try {
            this.authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new RuntimeException("Credenziali errate");
        }
    }
}
