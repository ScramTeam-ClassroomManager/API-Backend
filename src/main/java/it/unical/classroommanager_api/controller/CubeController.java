package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.CubeDto;
import it.unical.classroommanager_api.service.IService.ICubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.CUBEPATH)
public class CubeController {

    @Autowired
    private ICubeService cubeService;

    @PostMapping(APIConstant.ADDCUBE)
    public ResponseEntity<CubeDto> addCube(@RequestBody CubeDto cubeDto) {
        CubeDto createdCube = cubeService.addCube(cubeDto);
        return new ResponseEntity<>(createdCube, HttpStatus.CREATED);
    }

    @GetMapping(APIConstant.ALLCUBES)
    public ResponseEntity<List<CubeDto>> getAllCubi() {
        List<CubeDto> cubi = cubeService.getAllCubi();
        return new ResponseEntity<>(cubi, HttpStatus.OK);
    }

    @GetMapping(APIConstant.CUBES_BY_DEPARTMENT + "/{departmentId}")
    public ResponseEntity<List<Integer>> getCubesByDepartment(@PathVariable long departmentId) {
        List<Integer> cubes = cubeService.getCubesByDepartment(departmentId);
        return new ResponseEntity<>(cubes, HttpStatus.OK);
    }


}
