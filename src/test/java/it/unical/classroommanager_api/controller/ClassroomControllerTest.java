package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.IService.IClassService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ClassroomControllerTest {

    @Mock
    private IClassService classService;

    @InjectMocks
    private ClassroomController classroomController;

    @Mock
    private JWTService jwtService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllClassrooms() {
        List<ClassroomDto> classrooms = new ArrayList<>();
        ClassroomDto classroom1 = new ClassroomDto();
        classroom1.setId(1L);
        classroom1.setName("MT5");
        classroom1.setAvailable(true);

        ClassroomDto classroom2 = new ClassroomDto();
        classroom2.setId(2L);
        classroom2.setName("MT1");
        classroom2.setAvailable(true);

        classrooms.add(classroom1);
        classrooms.add(classroom2);

        when(classService.getAllClassrooms()).thenReturn(classrooms);

        List<ClassroomDto> result = classroomController.getAllClassrooms();

        assertEquals(2, result.size());
        assertEquals("MT5", result.get(0).getName());
        assertEquals("MT1", result.get(1).getName());
        verify(classService, times(1)).getAllClassrooms();
    }

    @Test
    void updateAvailableStatus_Successful() {
        long classroomId = 1L;

        ClassroomDto updatedClassroom = new ClassroomDto();
        updatedClassroom.setId(classroomId);
        updatedClassroom.setAvailable(false);

        when(classService.updateClassroom(classroomId)).thenReturn(updatedClassroom);

        ResponseEntity<ClassroomDto> response = classroomController.updateAvailableStatus(classroomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody().isAvailable());
        verify(classService, times(1)).updateClassroom(classroomId);
    }

    @Test
    void testUpdateProjectorStatus_ClassroomNotFound() {
        long classroomId = 1L;

        when(classService.updateClassroom(classroomId)).thenThrow(new RuntimeException("Nessuna aula trovata con il seguente id: " + classroomId));

        try {
            classroomController.updateAvailableStatus(classroomId);
        } catch (RuntimeException e) {
            assertEquals("Nessuna aula trovata con il seguente id: " + classroomId, e.getMessage());
        }

        verify(classService, times(1)).updateClassroom(classroomId);
    }

    @Test
    void addClassroom_WhenAdmin_ShouldReturnCreated() {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setName("Aula Magna");
        classroomDto.setCubeNumber(1);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer fake_token");
        when(jwtService.extractRole("fake_token")).thenReturn("ADMIN");
        when(classService.addClassroom(any(ClassroomDto.class))).thenReturn(classroomDto);

        ResponseEntity<ClassroomDto> response = classroomController.addClassroom(classroomDto, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(classroomDto, response.getBody());
    }

    @Test
    void addClassroom_WhenNotAdmin_ShouldThrowAccessDeniedException() {

        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setName("Aula Magna");
        classroomDto.setCubeNumber(1);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer fake_token");
        when(jwtService.extractRole("fake_token")).thenReturn("USER"); // Non admin

        assertThrows(AccessDeniedException.class, () -> {
            classroomController.addClassroom(classroomDto, request);
        });

        verify(classService, never()).addClassroom(any());
    }

    @Test
    void testGetClassroomNameById_ClassroomFound() {
        long classroomId = 1L;
        String classroomName = "MT5";

        when(classService.getClassroomNameById(classroomId)).thenReturn(classroomName);

        ResponseEntity<String> response = classroomController.getClassroomNameById(classroomId);
        assert response.getStatusCode().equals(HttpStatus.OK);
        verify(classService, times(1)).getClassroomNameById(classroomId);
        assertEquals(classroomName, response.getBody());

    }

    @Test
    void testGetClassroomNameByCubeNumber() {
        int cubeNumber = 1;
        List<ClassroomDto> classrooms = new ArrayList<>();
        ClassroomDto classroom1 = new ClassroomDto();
        classroom1.setId(1L);
        classroom1.setName("MT5");
        classroom1.setAvailable(true);
        classroom1.setCubeNumber(cubeNumber);

        ClassroomDto classroom2 = new ClassroomDto();
        classroom2.setId(2L);
        classroom2.setName("MT1");
        classroom2.setAvailable(true);
        classroom2.setCubeNumber(cubeNumber);

        classrooms.add(classroom1);
        classrooms.add(classroom2);

        when(classService.getClassroomsByCubeNumber(cubeNumber)).thenReturn(classrooms);
        ResponseEntity<List<ClassroomDto>> response = classroomController.getClassroomsByCubeNumber(cubeNumber);

        List<ClassroomDto> result = response.getBody();

        assertEquals(2, result.size());
        assertEquals("MT5", result.get(0).getName());
        assertEquals("MT1", result.get(1).getName());
        verify(classService, times(1)).getClassroomsByCubeNumber(cubeNumber);
    }

    @Test
    void updateClassroomDetails_WhenAdmin_ShouldReturnOk() {
        long classroomId = 1L;
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setName("MT5");
        classroomDto.setCubeNumber(1);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer fake_token");
        when(jwtService.extractRole("fake_token")).thenReturn("ADMIN");
        when(classService.updateClassroomDetails(classroomId, classroomDto)).thenReturn(classroomDto);

        ResponseEntity<ClassroomDto> response = classroomController.updateClassroomDetails(classroomId, classroomDto, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(classroomDto, response.getBody());
    }

    @Test
    void updateClassroomDetails_WhenNotAdmin_ShouldThrowAccessDeniedException() {
        long classroomId = 1L;
        ClassroomDto classroomDto = new ClassroomDto();
        classroomDto.setName("MT5");
        classroomDto.setCubeNumber(1);

        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer fake_token");
        when(jwtService.extractRole("fake_token")).thenReturn("USER"); // Non admin

        assertThrows(AccessDeniedException.class, () -> {
            classroomController.updateClassroomDetails(classroomId, classroomDto, request);
        });

        verify(classService, never()).updateClassroomDetails(anyLong(), any());
    }

    @Test
    void getClassroomByDepartment() {
        long departmentId = 1L;
        List<ClassroomDto> classrooms = new ArrayList<>();
        ClassroomDto classroom1 = new ClassroomDto();
        classroom1.setId(1L);
        classroom1.setName("MT5");
        classroom1.setAvailable(true);
        classroom1.setCubeNumber(1);

        ClassroomDto classroom2 = new ClassroomDto();
        classroom2.setId(2L);
        classroom2.setName("MT1");
        classroom2.setAvailable(true);
        classroom2.setCubeNumber(1);

        classrooms.add(classroom1);
        classrooms.add(classroom2);

        when(classService.getClassroomsByDepartment(departmentId)).thenReturn(classrooms);
        ResponseEntity<List<ClassroomDto>> response = classroomController.getClassroomsByDepartment(departmentId);

        List<ClassroomDto> result = response.getBody();

        assertEquals(2, result.size());
        assertEquals("MT5", result.get(0).getName());
        assertEquals("MT1", result.get(1).getName());
        verify(classService, times(1)).getClassroomsByDepartment(departmentId);
    }

    @Test
    void getClassroomByName() {
        String classroomName = "MT5";
        ClassroomDto classroom = new ClassroomDto();
        classroom.setId(1L);
        classroom.setName(classroomName);
        classroom.setAvailable(true);
        classroom.setCubeNumber(1);

        when(classService.getClassroomByName(classroomName)).thenReturn(classroom);
        ResponseEntity<ClassroomDto> response = classroomController.getClassroomByName(classroomName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(classroomName, response.getBody().getName());
        verify(classService, times(1)).getClassroomByName(classroomName);
    }
}
