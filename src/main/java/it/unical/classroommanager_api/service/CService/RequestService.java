package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.entities.Request;
import it.unical.classroommanager_api.repository.ClassroomRepository;
import it.unical.classroommanager_api.repository.RequestRepository;
import it.unical.classroommanager_api.service.IService.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    public RequestDto createRequest(RequestDto requestDto, int userSerialNumber) {
        Request request = new Request();
        request.setReason(requestDto.getReason());

        Classroom classroom = classroomRepository.findById(requestDto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        request.setClassroom(classroom);

        request.setUserSerialNumber(userSerialNumber);
        request.setCreationDate(LocalDate.now());

        Request savedRequest = requestRepository.save(request);
        requestDto.setId(savedRequest.getId());
        requestDto.setCreationDate(savedRequest.getCreationDate());

        return requestDto;
    }

    public List<RequestDto> getAllRequests() {
        return requestRepository.findAll().stream()
                .map(request -> {
                    RequestDto dto = new RequestDto();
                    dto.setId(request.getId());
                    dto.setReason(request.getReason());
                    dto.setClassroomId(request.getClassroom().getId());
                    dto.setUserSerialNumber(request.getUserSerialNumber());
                    dto.setCreationDate(request.getCreationDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}

