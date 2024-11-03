package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.repository.ClassroomRepository;
import it.unical.classroommanager_api.service.IService.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService implements IClassService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ClassroomDto> getAllClassrooms() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream()
                .map(classroom -> modelMapper.map(classroom, ClassroomDto.class))
                .collect(Collectors.toList());
    }
}

