package it.unical.classroommanager_api.repository;

import it.unical.classroommanager_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySerialNumber(int serialNumber);
    Boolean existsBySerialNumberOrEmail(int serialNumber, String email);
}
