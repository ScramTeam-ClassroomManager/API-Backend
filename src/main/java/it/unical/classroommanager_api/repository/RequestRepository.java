package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.Request;
import it.unical.classroommanager_api.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatus(Status status);
    List<Request> findByStatusNot(Status status);
    List<Request> findByUserSerialNumber(int userSerialNumber);
  
    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END
    FROM Request r
    WHERE r.classroom.id = :classroomId
      AND r.requestDate = :requestDate
      AND r.status = 'ACCEPTED'
      AND (
           (r.startHour < :endHour AND r.endHour > :startHour)
      )
""")
    boolean existsOverlappingRequest(@Param("classroomId") Long classroomId,
                                     @Param("requestDate") LocalDate requestDate,
                                     @Param("startHour") LocalTime startHour,
                                     @Param("endHour") LocalTime endHour);

}
