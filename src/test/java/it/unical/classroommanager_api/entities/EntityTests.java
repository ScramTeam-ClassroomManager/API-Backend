package it.unical.classroommanager_api.entities;

import static org.junit.jupiter.api.Assertions.*;

import it.unical.classroommanager_api.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;


class EntityTests {

    @Test
    void testCubeFields() {
        Cube cube = new Cube();
        Department department = new Department();
        department.setId(1);
        department.setDepartment("Computer Science");

        cube.setNumber(1);
        cube.setDepartment(department);

        assertEquals(1, cube.getNumber());
        assertEquals("Computer Science", cube.getDepartment().getDepartment());
        assertEquals(1, cube.getDepartment().getId());
    }


    @Test
    void testClassroomFields() {
        Classroom classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("MT5");
        classroom.setAvailable(true);

        assertEquals(1L, classroom.getId());
        assertEquals("MT5", classroom.getName());
        assertTrue(classroom.isAvailable());
    }

    @Test
    void testUserFields() {
        User user = new User();
        user.setId(1L);
        user.setSerialNumber(11111);
        user.setPassword("password");
        user.setRole(Role.ADMIN);

        assertEquals(1L, user.getId());
        assertEquals(11111, user.getSerialNumber());
        assertEquals("password", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void testRequestFields() {
        Request request = new Request();
        request.setId(1L);
        request.setClassroom(new Classroom());
        request.setUserSerialNumber(11111);
        request.setStartHour(LocalTime.of(8, 0));
        request.setEndHour(LocalTime.of(10, 0));
        request.setRequestDate(LocalDate.of(2024, 10, 11));
        request.setCreationDate(LocalDate.of(2024, 10, 10));

        assertEquals(1L, request.getId());
        assertNotNull(request.getClassroom());
        assertNotNull(request.getUserSerialNumber());
        assertEquals(LocalTime.of(8, 0), request.getStartHour());
        assertEquals(LocalTime.of(10, 0), request.getEndHour());
        assertEquals(LocalDate.of(2024, 10, 11), request.getRequestDate());
        assertEquals(LocalDate.of(2024, 10, 10), request.getCreationDate());
    }
}
