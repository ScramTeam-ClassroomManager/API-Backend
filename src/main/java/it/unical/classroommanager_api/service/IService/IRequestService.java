package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.enums.Status;

import java.util.List;

public interface IRequestService {
    RequestDto createRequest(RequestDto requestDto, int userSerialNumber);
    List<RequestDto> getAllRequests();
    RequestDto updateRequestStatus(Long requestId, Status status);
    List<RequestDto> getNonPendingRequests();
}
