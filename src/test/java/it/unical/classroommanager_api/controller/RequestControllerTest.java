package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.dto.StatusDto;
import it.unical.classroommanager_api.enums.Status;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.CService.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;

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
        MockitoAnnotations.openMocks(this);}

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

    @Test
    void testGetAllPendingRequests() {
        RequestDto pendingRequest1 = new RequestDto();
        pendingRequest1.setId(1L);
        pendingRequest1.setReason("Richiesta per laboratorio");

        RequestDto pendingRequest2 = new RequestDto();
        pendingRequest2.setId(2L);
        pendingRequest2.setReason("Richiesta per progetto");

        List<RequestDto> pendingRequestList = Arrays.asList(pendingRequest1, pendingRequest2);

        when(requestService.getAllPendingRequests()).thenReturn(pendingRequestList);

        ResponseEntity<List<RequestDto>> response = requestController.getAllPendingRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pendingRequestList, response.getBody());
        verify(requestService, times(1)).getAllPendingRequests();
    }

    @Test
    void testUpdateRequestStatus() {
        Long requestId = 1L;
        Status newStatus = Status.ACCEPTED;
        String adminResponse = "Approved for the exam request";

        RequestDto updatedRequest = new RequestDto();
        updatedRequest.setId(requestId);
        updatedRequest.setReason("Richiesta per esame di Sistemi operativi");
        updatedRequest.setStatus(newStatus);
        updatedRequest.setAdminResponse(adminResponse);

        when(requestService.updateRequestStatus(requestId, newStatus, adminResponse)).thenReturn(updatedRequest);

        ResponseEntity<RequestDto> response = requestController.updateRequestStatus(requestId, newStatus, adminResponse);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedRequest, response.getBody());
        verify(requestService, times(1)).updateRequestStatus(requestId, newStatus, adminResponse);
    }

    @Test
    void deleteRequest() {
        Long requestId = 1L;
        String token = "Bearer some.token.value";
        int userSerialNumber = 123;

        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.extractSerialNumber(token.substring(7))).thenReturn(String.valueOf(userSerialNumber));
        when(requestService.deleteRequest(requestId, userSerialNumber)).thenReturn(true);

        ResponseEntity<Void> response = requestController.deleteRequest(requestId, httpServletRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(requestService, times(1)).deleteRequest(requestId, userSerialNumber);

    }

    @Test
    void testGetUserRequests() {
        RequestDto request1 = new RequestDto();
        request1.setId(1L);
        request1.setReason("Richiesta per esame di Sistemi operativi");

        RequestDto request2 = new RequestDto();
        request2.setId(2L);
        request2.setReason("Richiesta per seminario");

        List<RequestDto> requestList = Arrays.asList(request1, request2);

        String token = "Bearer some.token.value";
        int userSerialNumber = 123;
        when(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(token);
        when(jwtService.extractSerialNumber(token.substring(7))).thenReturn(String.valueOf(userSerialNumber));
        when(requestService.getRequestsByUser(userSerialNumber)).thenReturn(requestList);

        ResponseEntity<List<RequestDto>> response = requestController.getUserRequests(httpServletRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestList, response.getBody());
        verify(requestService, times(1)).getRequestsByUser(userSerialNumber);
    }

    @Test
    void testGetAcceptedClassroomRequests() {
        Long classroomId = 1L;
        RequestDto request1 = new RequestDto();
        request1.setId(1L);
        request1.setReason("Richiesta per esame di Sistemi operativi");

        RequestDto request2 = new RequestDto();
        request2.setId(2L);
        request2.setReason("Richiesta per seminario");

        List<RequestDto> requestList = Arrays.asList(request1, request2);

        when(requestService.getAcceptedClassroomRequests(classroomId)).thenReturn(requestList);

        ResponseEntity<List<RequestDto>> response = requestController.getAcceptedClassroomRequests(classroomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestList, response.getBody());
        verify(requestService, times(1)).getAcceptedClassroomRequests(classroomId);
    }

    @Test
    void testGetNonPendingRequests() {
        RequestDto request1 = new RequestDto();
        request1.setId(1L);
        request1.setReason("Richiesta per esame di Sistemi operativi");

        RequestDto request2 = new RequestDto();
        request2.setId(2L);
        request2.setReason("Richiesta per seminario");

        List<RequestDto> requestList = Arrays.asList(request1, request2);
        when(requestService.getNonPendingRequests()).thenReturn(requestList);

        ResponseEntity<List<RequestDto>> response = requestController.getNonPendingRequests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(requestList, response.getBody());
        verify(requestService, times(1)).getNonPendingRequests();
    }

}

