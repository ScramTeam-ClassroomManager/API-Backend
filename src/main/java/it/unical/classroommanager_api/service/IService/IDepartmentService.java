package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.DepartmentDto;

import java.util.List;

public interface IDepartmentService {

    List<DepartmentDto> getAllDepartments();
}
