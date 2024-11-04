package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.service.IService.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/updateProjector/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClassroomDto updateProjectorStatus(@PathVariable long id) {
        return classService.updateProjectorStatus(id);
    }
}
