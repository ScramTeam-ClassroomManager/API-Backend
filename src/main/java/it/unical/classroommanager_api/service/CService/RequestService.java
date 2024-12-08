package it.unical.classroommanager_api.service.CService;

import it.unical.classroommanager_api.dto.RequestDto;
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
        request.setAdminResponse(requestDto.getAdminResponse());

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

        if (requestRepository.existsOverlappingRequest(classroom.getId(), requestDto.getRequestDate(), requestDto.getStartHour(), requestDto.getEndHour())) {
            return null;
        }

        request.setStatus(Status.PENDING);

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
                    dto.setStatus(request.getStatus());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setRequestDate(request.getRequestDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<RequestDto> getNonPendingRequests() {
        return requestRepository.findByStatusNot(Status.PENDING).stream()
                .map(request -> {
                    RequestDto dto = new RequestDto();
                    dto.setId(request.getId());
                    dto.setReason(request.getReason());
                    dto.setClassroomId(request.getClassroom().getId());
                    dto.setUserSerialNumber(request.getUserSerialNumber());
                    dto.setCreationDate(request.getCreationDate());
                    dto.setAdminResponse(request.getAdminResponse());
                    dto.setStatus(request.getStatus());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setRequestDate(request.getRequestDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<RequestDto> getAllPendingRequests() {
        return requestRepository.findByStatus(Status.PENDING).stream()
                .map(request -> {
                    RequestDto dto = new RequestDto();
                    dto.setId(request.getId());
                    dto.setReason(request.getReason());
                    dto.setClassroomId(request.getClassroom().getId());
                    dto.setUserSerialNumber(request.getUserSerialNumber());
                    dto.setCreationDate(request.getCreationDate());
                    dto.setStatus(request.getStatus());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setRequestDate(request.getRequestDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public RequestDto updateRequestStatus(Long requestId, Status status, String adminResponse) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

        request.setStatus(status);
        request.setAdminResponse(adminResponse);

        Request updatedRequest = requestRepository.save(request);

        return modelMapper.map(updatedRequest, RequestDto.class);
    }


    public List<RequestDto> getRequestsByUser(int userSerialNumber) {
        return requestRepository.findByUserSerialNumber(userSerialNumber).stream()
                .map(request -> {
                    RequestDto dto = new RequestDto();
                    dto.setId(request.getId());
                    dto.setReason(request.getReason());
                    dto.setClassroomId(request.getClassroom().getId());
                    dto.setUserSerialNumber(request.getUserSerialNumber());
                    dto.setCreationDate(request.getCreationDate());
                    dto.setAdminResponse(request.getAdminResponse());
                    dto.setStatus(request.getStatus());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setRequestDate(request.getRequestDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public boolean deleteRequest(Long requestId, int userSerialNumber) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found with id: " + requestId));

        if (request.getUserSerialNumber() == userSerialNumber ||
                userRepository.findBySerialNumber(userSerialNumber)
                        .map(User::getRole)
                        .orElseThrow(() -> new RuntimeException("User not found"))
                        .equals(Role.ADMIN)) {
            requestRepository.delete(request);
            return true;
        }
        return false;
    }

    public List<RequestDto> getAcceptedClassroomRequests(Long id){
        return requestRepository.findByClassroomIdAndStatus(id, Status.ACCEPTED).stream()
                .map(request -> {
                    RequestDto dto = new RequestDto();
                    dto.setId(request.getId());
                    dto.setReason(request.getReason());
                    dto.setClassroomId(request.getClassroom().getId());
                    dto.setUserSerialNumber(request.getUserSerialNumber());
                    dto.setCreationDate(request.getCreationDate());
                    dto.setStatus(request.getStatus());
                    dto.setStartHour(request.getStartHour());
                    dto.setEndHour(request.getEndHour());
                    dto.setRequestDate(request.getRequestDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}

