package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.Cube;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CubeRepository extends JpaRepository<Cube, Integer> {
    Optional<Cube> findByNumber(int number);
    List<Cube> findByDepartmentId(long departmentId);
}
