package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.dto.DepartmentDto;
import it.unical.classroommanager_api.service.IService.IDepartmentService;
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

public class DepartmentControllerTest {

    @Mock
    private IDepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDepartments() {
        List<DepartmentDto> departments = new ArrayList<>();
        DepartmentDto department1 = new DepartmentDto();
        department1.setId(1L);
        department1.setDepartment("Matematica e Informatica");

        DepartmentDto department2 = new DepartmentDto();
        department2.setId(2L);
        department2.setDepartment("Fisica");

        departments.add(department1);
        departments.add(department2);

        when(departmentService.getAllDepartments()).thenReturn(departments);

        List<DepartmentDto> result = departmentController.getAllDepartments();

        assertEquals(2, result.size());
        assertEquals("Matematica e Informatica", result.get(0).getDepartment());
        assertEquals("Fisica", result.get(1).getDepartment());
        verify(departmentService, times(1)).getAllDepartments();
    }

    @Test
    void testGetDepartmentByClassroom() {
        long id = 1L;
        String department = "Matematica e Informatica";

        when(departmentService.getDepartmentByClassroom(id)).thenReturn(department);

        String result = departmentController.getDepartmentByClassroom(id);

        assertEquals(department, result);
        verify(departmentService, times(1)).getDepartmentByClassroom(id);
    }
}
