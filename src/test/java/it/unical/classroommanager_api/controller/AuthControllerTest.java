package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.LoginDto;
import it.unical.classroommanager_api.dto.RegisterDto;
import it.unical.classroommanager_api.dto.UserDto;
import it.unical.classroommanager_api.security.CustomUserDetailsService;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.IService.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTService jwtService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private IUserService userService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Successful() {
        LoginDto loginDto = new LoginDto();
        loginDto.setSerialNumber("12345");
        loginDto.setPassword("admin");

        UserDetails userDetails = mock(UserDetails.class);
        when(customUserDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("testToken");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        ResponseEntity<Map<String, String>> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("testToken", response.getBody().get("token"));
    }


    @Test
    void testLogin_InvalidCredentials() {
        LoginDto loginDto = new LoginDto();
        loginDto.setSerialNumber("1234");
        loginDto.setPassword("PasswordErrata");

        doThrow(new BadCredentialsException("Credenziali errate")).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        try {
            authController.login(loginDto);
        } catch (RuntimeException e) {
            assertEquals("Credenziali errate", e.getMessage());
        }
    }

    @Test
    void testRegister_Successful() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setSerialNumber(1234);
        registerDto.setFirstName("Nome");
        registerDto.setLastName("Cognome");
        registerDto.setEmail("nomcogn@example.com");
        registerDto.setPassword("password");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setSerialNumber(1234);
        userDto.setFirstName("Nome");
        userDto.setLastName("Cognome");
        userDto.setEmail("nomcogn@example.com");

        when(userService.registerUser(any(RegisterDto.class))).thenReturn(userDto);

        ResponseEntity<UserDto> response = authController.register(registerDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testRegister_UserAlreadyExists() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setSerialNumber(1234);
        registerDto.setFirstName("Nome");
        registerDto.setLastName("Cognome");
        registerDto.setEmail("nomcogn@example.com");
        registerDto.setPassword("password");

        when(userService.registerUser(any(RegisterDto.class)))
                .thenThrow(new RuntimeException("Utente già esistente"));

        try {
            authController.register(registerDto);
        } catch (RuntimeException e) {
            assertEquals("Utente già esistente", e.getMessage());
        }
    }
}

