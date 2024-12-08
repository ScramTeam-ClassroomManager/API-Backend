package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.DepartmentDto;
import it.unical.classroommanager_api.service.IService.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.DEPARTMENTPATH)
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping(APIConstant.ALLDEPARTMENTS)
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping(APIConstant.GETDEPARTMENTBYCLASSROOM + "/{id}")
    public String getDepartmentByClassroom(@PathVariable long id) {
        return departmentService.getDepartmentByClassroom(id);
    }
}
