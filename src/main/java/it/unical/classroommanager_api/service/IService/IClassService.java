package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.ClassroomDto;

import java.util.List;

public interface IClassService {
    List<ClassroomDto> getAllClassrooms();
    void updateClassroom(long id);
}

