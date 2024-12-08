package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("""
    SELECT d.department FROM Department d, Classroom cl\s
    WHERE cl.cube.department.id = d.id
    AND cl.id = :classroomId
   \s""")
    String findByClassrromId(@Param("classroomId") String classroomId);
}
