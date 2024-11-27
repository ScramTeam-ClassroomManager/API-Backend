package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.Classroom;
import it.unical.classroommanager_api.entities.Cube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findClassroomById(Long id);
    List<Classroom> findByCube(Cube cube);
}
