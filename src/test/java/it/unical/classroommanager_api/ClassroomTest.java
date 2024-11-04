package it.unical.classroommanager_api;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.repository.ClassroomRepository;
import it.unical.classroommanager_api.service.CService.ClassService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClassServiceTest {

    @InjectMocks
    private ClassService classService;

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private ModelMapper modelMapper;

    private Classroom classroom;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("MT5");
        classroom.setCube(2);
        classroom.setFloor(1);
        classroom.setCapability(30);
        classroom.setNumSocket(4);
        classroom.setProjector(false);
    }

    @Test
    void testUpdateProjectorStatusSuccess() {
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        ClassroomDto classroomDto = new ClassroomDto();
        when(modelMapper.map(any(Classroom.class), eq(ClassroomDto.class))).thenReturn(classroomDto);

        ClassroomDto updatedClassroomDto = classService.updateClassroom(1L);

        assertEquals(true, updatedClassroomDto.isProjector());
        verify(classroomRepository, times(1)).findById(1L);
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    void testUpdateProjectorStatusClassroomNotFound() {
        when(classroomRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> classService.updateClassroom(1L));

        verify(classroomRepository, times(1)).findById(1L);
        verify(classroomRepository, never()).save(any(Classroom.class));
    }
}



