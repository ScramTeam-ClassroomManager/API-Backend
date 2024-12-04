package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.CubeDto;
import it.unical.classroommanager_api.dto.DepartmentDto;
import it.unical.classroommanager_api.service.IService.ICubeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CubeControllerTest {

    @InjectMocks
    private CubeController cubeController;

    @Mock
    private ICubeService cubeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCube() {
        List<CubeDto> cubes = new ArrayList<>();

        DepartmentDto department1 = new DepartmentDto();
        department1.setId(1);
        department1.setDepartment("Computer Science");

        DepartmentDto department2 = new DepartmentDto();
        department2.setId(2);
        department2.setDepartment("Science");

        CubeDto cube1 = new CubeDto();
        cube1.setNumber(1);
        cube1.setDepartment(department1);

        CubeDto cube2 = new CubeDto();
        cube2.setNumber(2);
        cube2.setDepartment(department2);

        cubes.add(cube1);
        cubes.add(cube2);

        when(cubeService.getAllCubi()).thenReturn(cubes);

        List<CubeDto> result = cubeController.getAllCubi().getBody();

        assert result != null;
        assertEquals(2, result.size());
        assertEquals("Computer Science", result.get(0).getDepartment().getDepartment());
        assertEquals("Science", result.get(1).getDepartment().getDepartment());
        verify(cubeService, times(1)).getAllCubi();
    }

    @Test
    void addCube_ShouldReturnCreatedCube() {
        DepartmentDto department = new DepartmentDto();
        department.setId(1);
        department.setDepartment("Computer Science");

        CubeDto cubeDto = new CubeDto();
        cubeDto.setNumber(1);
        cubeDto.setDepartment(department);

        when(cubeService.addCube(any(CubeDto.class))).thenReturn(cubeDto);

        ResponseEntity<CubeDto> response = cubeController.addCube(cubeDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cubeDto, response.getBody());

        verify(cubeService, times(1)).addCube(any(CubeDto.class));
    }



}
