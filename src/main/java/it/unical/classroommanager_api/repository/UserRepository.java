package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findBySerialNumber(int serialNumber);
}
