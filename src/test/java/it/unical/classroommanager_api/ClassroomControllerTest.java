package it.unical.classroommanager_api;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.service.IService.IClassService;
import it.unical.classroommanager_api.controller.ClassroomController;
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

class ClassroomControllerTest {

    @Mock
    private IClassService classService;

    @InjectMocks
    private ClassroomController classroomController;

    private List<ClassroomDto> classroomDtoList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        classroomDtoList = new ArrayList<>();
        ClassroomDto classroomDto1 = new ClassroomDto();
        classroomDto1.setId(1L);
        classroomDto1.setName("MT5");
        classroomDto1.setCube(101);
        classroomDto1.setFloor(1);
        classroomDto1.setCapability(30);
        classroomDto1.setNumSocket(4);
        classroomDto1.setProjector(true);

        ClassroomDto classroomDto2 = new ClassroomDto();
        classroomDto2.setId(2L);
        classroomDto2.setName("MT1");
        classroomDto2.setCube(102);
        classroomDto2.setFloor(1);
        classroomDto2.setCapability(50);
        classroomDto2.setNumSocket(6);
        classroomDto2.setProjector(false);

        classroomDtoList.add(classroomDto1);
        classroomDtoList.add(classroomDto2);
    }

    @Test
    void testGetAllClassrooms() {
        when(classService.getAllClassrooms()).thenReturn(classroomDtoList);

        ResponseEntity<List<ClassroomDto>> responseEntity = classroomController.getAllClassrooms();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<ClassroomDto> result = responseEntity.getBody();
        assertEquals(2, result.size());
        assertEquals("MT5", result.get(0).getName());
        assertEquals("MT1", result.get(1).getName());

        verify(classService, times(1)).getAllClassrooms();
    }
}


