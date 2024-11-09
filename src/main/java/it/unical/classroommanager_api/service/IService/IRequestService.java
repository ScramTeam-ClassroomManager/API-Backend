package it.unical.classroommanager_api.service.IService;

import it.unical.classroommanager_api.dto.RequestDto;

import java.util.List;

public interface IRequestService {
    RequestDto createRequest(RequestDto requestDto, int userSerialNumber);
    List<RequestDto> getAllRequests();
}
