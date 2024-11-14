package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.dto.StatusDto;
import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.entities.Request;
import it.unical.classroommanager_api.entities.User;
import it.unical.classroommanager_api.enums.ClassroomType;
import it.unical.classroommanager_api.enums.Role;
import it.unical.classroommanager_api.enums.Status;
import it.unical.classroommanager_api.repository.ClassroomRepository;
import it.unical.classroommanager_api.repository.RequestRepository;
import it.unical.classroommanager_api.repository.UserRepository;
import it.unical.classroommanager_api.service.IService.IRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RequestDto createRequest(RequestDto requestDto, int userSerialNumber) {
        Request request = new Request();
        request.setReason(requestDto.getReason());

        Classroom classroom = classroomRepository.findById(requestDto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        request.setClassroom(classroom);

        User user = userRepository.findBySerialNumber(userSerialNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        request.setUserSerialNumber(userSerialNumber);
        request.setCreationDate(LocalDate.now());
        request.setRequestDate(requestDto.getRequestDate());
        request.setStartHour(requestDto.getStartHour());
        request.setEndHour(requestDto.getEndHour());

        if (classroom.getType() == ClassroomType.AUDITORIUM) {
            request.setStatus(Status.PENDING);
        } else {
            if (user.getRole() == Role.PROFESSOR) {
                request.setStatus(Status.ACCEPTED);
            } else {
                request.setStatus(Status.PENDING);
            }
        }


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
                    dto.setRequestDate(request.getRequestDate());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setStatus(request.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public RequestDto updateStatusRequest(long id, StatusDto statusDto){
        Optional<Request> request = Optional.ofNullable(requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + id)));
        request.get().setStatus(statusDto.getStatus());
        requestRepository.save(request.get());
        return modelMapper.map(request.get(), RequestDto.class);
    }

}

