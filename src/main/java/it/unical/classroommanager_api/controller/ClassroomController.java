package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.service.IService.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.CLASSPATH)
public class ClassroomController {

    @Autowired
    private IClassService classService;

    @GetMapping(APIConstant.ALLCLASS)
    public List<ClassroomDto> getAllClassrooms() {
        return classService.getAllClassrooms();
    }
}
