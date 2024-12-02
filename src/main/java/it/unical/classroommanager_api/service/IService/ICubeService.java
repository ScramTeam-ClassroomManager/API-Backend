package it.unical.classroommanager_api.service.IService;


import it.unical.classroommanager_api.dto.CubeDto;

import java.util.List;

public interface ICubeService {
    CubeDto addCube(CubeDto cubeDto);
    List<CubeDto> getAllCubi();
}
