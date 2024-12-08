package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.enums.Status;

import java.util.List;

public interface IRequestService {
    RequestDto createRequest(RequestDto requestDto, int userSerialNumber);
    List<RequestDto> getAllRequests();
    RequestDto updateRequestStatus(Long requestId, Status status, String adminResponse);
    List<RequestDto> getNonPendingRequests();
    List<RequestDto> getRequestsByUser(int userSerialNumber);
    boolean deleteRequest(Long requestId, int userSerialNumber);


}
