package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.ClassroomDto;

import java.util.List;

public interface IClassService {
    List<ClassroomDto> getAllClassrooms();
    ClassroomDto updateClassroom(long id);
    ClassroomDto addClassroom(ClassroomDto classroomDto);
    String getClassroomNameById(long id);
    List<ClassroomDto> getClassroomsByCubeNumber(int cubeNumber);
    ClassroomDto updateClassroomDetails(long id, ClassroomDto classroomDto);
    List<ClassroomDto> getClassroomsByDepartment(long departmentId);
    ClassroomDto getClassroomByName(String name);


}

