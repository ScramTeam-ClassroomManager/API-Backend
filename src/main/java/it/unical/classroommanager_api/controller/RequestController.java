package it.unical.classroommanager_api.controller;

import it.unical.classroommanager_api.constant.APIConstant;
import it.unical.classroommanager_api.dto.RequestDto;
import it.unical.classroommanager_api.dto.StatusDto;
import it.unical.classroommanager_api.enums.Status;
import it.unical.classroommanager_api.security.JWTService;
import it.unical.classroommanager_api.service.CService.RequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstant.BASEPATH + APIConstant.REQUESTPATH)
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = APIConstant.ADDREQUEST)
    public ResponseEntity<RequestDto> createRequest(@RequestBody @Valid RequestDto requestDto, HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        int userSerialNumber = Integer.parseInt(jwtService.extractSerialNumber(token));

        RequestDto createdRequest = requestService.createRequest(requestDto, userSerialNumber);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @GetMapping(APIConstant.ALLREQUEST)
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.getAllRequests();
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @PutMapping(APIConstant.UPDATESTATUSREQUEST+"/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RequestDto> updateRequest(@PathVariable long id, @RequestBody StatusDto statusDto) {
        return new ResponseEntity<>(requestService.updateStatusRequest(id, statusDto), HttpStatus.OK);
    }
}


