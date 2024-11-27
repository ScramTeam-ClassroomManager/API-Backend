package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.ClassroomDto;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.IService.IClassService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.CLASSPATH)
public class ClassroomController {

    @Autowired
    private IClassService classService;

    @Autowired
    private JWTService jwtService;

    @GetMapping(APIConstant.ALLCLASS)
    public List<ClassroomDto> getAllClassrooms() {
        return classService.getAllClassrooms();
    }

    @PutMapping(APIConstant.BOOKING+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClassroomDto> updateAvailableStatus(@PathVariable long id) {
        return new ResponseEntity(classService.updateClassroom(id),HttpStatus.OK);
    }

    @PostMapping(APIConstant.ADDCLASS)
    public ResponseEntity<ClassroomDto> addClassroom(
            @RequestBody @Valid ClassroomDto classroomDto,
            HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String role = jwtService.extractRole(token);
        if (!role.equals("ADMIN")) {
            throw new AccessDeniedException("Access denied: User does not have ADMIN privileges.");
        }

        ClassroomDto createdClassroom = classService.addClassroom(classroomDto);
        return new ResponseEntity<>(createdClassroom, HttpStatus.CREATED);
    }



    @GetMapping(APIConstant.GETCLASSNAME + "/{id}")
    public ResponseEntity<String> getClassroomNameById(@PathVariable long id) {
        String classroomName = classService.getClassroomNameById(id);
        if (classroomName != null) {
            return new ResponseEntity<>(classroomName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Classroom not found", HttpStatus.NOT_FOUND);
        }
    }
}
