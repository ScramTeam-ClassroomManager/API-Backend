package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.DepartmentDto;
import it.unical.classroommanager_api.entities.Department;
import it.unical.classroommanager_api.repository.DepartmentRepository;
import it.unical.classroommanager_api.service.IService.IDepartmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(department -> modelMapper.map(department, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getDepartmentByClassroom(long id) {
        return departmentRepository.findByClassrromId(String.valueOf(id));
    }
}
