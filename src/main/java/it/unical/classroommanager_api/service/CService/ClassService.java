package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.entities.Cube;
import it.unical.classroommanager_api.repository.ClassroomRepository;
import it.unical.classroommanager_api.repository.CubeRepository;
import it.unical.classroommanager_api.service.IService.IClassService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassService implements IClassService {

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private CubeRepository cubeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<ClassroomDto> getAllClassrooms() {
        List<Classroom> classrooms = classroomRepository.findAll();
        return classrooms.stream()
                .map(classroom -> modelMapper.map(classroom, ClassroomDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public ClassroomDto updateClassroom(long id) {
        Optional<Classroom> classroom = Optional.ofNullable(classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id)));
        classroom.get().setAvailable(false);
        classroomRepository.save(classroom.get());
        return modelMapper.map(classroom.get(), ClassroomDto.class);
    }

    @Override
    public ClassroomDto addClassroom(ClassroomDto classroomDto) {
        Classroom classroom = modelMapper.map(classroomDto, Classroom.class);

        Optional<Cube> cube = cubeRepository.findByNumber(classroomDto.getCubeNumber());
        System.out.println(classroomDto.getCubeNumber());
        System.out.println(cube);
        if (!cube.isPresent()) {
            throw new RuntimeException("Cube not found with number: " + classroomDto.getCubeNumber());
        }
        classroom.setCube(cube.get());

        Classroom savedClassroom = classroomRepository.save(classroom);
        return modelMapper.map(savedClassroom, ClassroomDto.class);
    }

    @Override
    public String getClassroomNameById(long id) {
        Optional<Classroom> classroom = classroomRepository.findById(id);
        if (classroom.isPresent()) {
            return classroom.get().getName();
        } else {
            return null;
        }
    }

    @Override
    public List<ClassroomDto> getClassroomsByCubeNumber(int cubeNumber) {
        Optional<Cube> cube = cubeRepository.findByNumber(cubeNumber);
        if (!cube.isPresent()) {
            throw new RuntimeException("Cube not found with number: " + cubeNumber);
        }

        List<Classroom> classrooms = classroomRepository.findByCube(cube.get());
        return classrooms.stream()
                .map(classroom -> modelMapper.map(classroom, ClassroomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClassroomDto updateClassroomDetails(long id, ClassroomDto classroomDto) {
        Classroom existingClassroom = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classroom not found with id: " + id));

        existingClassroom.setName(classroomDto.getName());
        existingClassroom.setAvailable(classroomDto.isAvailable());
        existingClassroom.setCapability(classroomDto.getCapability());
        existingClassroom.setFloor(classroomDto.getFloor());
        existingClassroom.setProjector(classroomDto.isProjector());
        existingClassroom.setNumSocket(classroomDto.getNumSocket());
        existingClassroom.setType(classroomDto.getType());

        Optional<Cube> cube = cubeRepository.findByNumber(classroomDto.getCubeNumber());
        if (!cube.isPresent()) {
            throw new RuntimeException("Cube not found with number: " + classroomDto.getCubeNumber());
        }
        existingClassroom.setCube(cube.get());

        Classroom updatedClassroom = classroomRepository.save(existingClassroom);

        return modelMapper.map(updatedClassroom, ClassroomDto.class);
    }


}

