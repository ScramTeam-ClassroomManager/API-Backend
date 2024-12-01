package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.CubeDto;
import it.unical.classroommanager_api.entities.Cube;
import it.unical.classroommanager_api.repository.CubeRepository;
import it.unical.classroommanager_api.service.IService.ICubeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CubeService implements ICubeService {

    @Autowired
    private CubeRepository cubeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CubeDto addCube(CubeDto cubeDto) {
        Cube cube = modelMapper.map(cubeDto, Cube.class);
        Cube savedCube = cubeRepository.save(cube);
        return modelMapper.map(savedCube, CubeDto.class);
    }

    @Override
    public List<CubeDto> getAllCubi() {
        List<Cube> cubi = cubeRepository.findAll();
        return cubi.stream()
                .map(cube -> modelMapper.map(cube, CubeDto.class))
                .collect(Collectors.toList());
    }

}
