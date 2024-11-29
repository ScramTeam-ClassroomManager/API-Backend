package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.Request;
import it.unical.classroommanager_api.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatus(Status status);
    List<Request> findByStatusNot(Status status);
    List<Request> findByUserSerialNumber(int userSerialNumber);
}
