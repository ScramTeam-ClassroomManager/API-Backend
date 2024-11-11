package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.CService.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RequestControllerTest {

    @Mock
    private RequestService requestService;

    @Mock
    private JWTService jwtService;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private RequestController requestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRequest() {
        RequestDto requestDto = new RequestDto();
        requestDto.setReason("Richiesta per esame di Sistemi operativi");
        RequestDto createdRequest = new RequestDto();
        createdRequest.setId(1L);
        createdRequest.setReason("Richiesta per esame di Sistemi operativi");

        String token = "Bearer some.token.value";
        int userSerialNumber = 123;
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.extractSerialNumber(token.substring(7))).thenReturn(String.valueOf(userSerialNumber));
        when(requestService.createRequest(requestDto, userSerialNumber)).thenReturn(createdRequest);

        ResponseEntity<RequestDto> response = requestController.createRequest(requestDto, httpServletRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdRequest, response.getBody());
        verify(requestService, times(1)).createRequest(requestDto, userSerialNumber);
    }

    @Test
    void testGetAllRequests() {
        RequestDto request1 = new RequestDto();
        request1.setId(1L);
        request1.setReason("Richiesta per esame di Sistemi operativi");

        RequestDto request2 = new RequestDto();
        request2.setId(2L);
        request2.setReason("Richiesta per seminario");

        List<RequestDto> requestList = Arrays.asList(request1, request2);
        when(requestService.getAllRequests()).thenReturn(requestList);

        ResponseEntity<List<RequestDto>> response = requestController.getAllRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestList, response.getBody());
        verify(requestService, times(1)).getAllRequests();
    }
}

