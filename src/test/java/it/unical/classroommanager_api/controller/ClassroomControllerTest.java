package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.service.IService.IClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClassroomControllerTest {

    @Mock
    private IClassService classService;

    @InjectMocks
    private ClassroomController classroomController;

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
}
