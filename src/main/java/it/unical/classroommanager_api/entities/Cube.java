package it.unical.classroommanager_api.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CUBES")
public class Cube {

    @Id
    @Column(name = "NUMBER", nullable = false, unique = true)
    private int number;

    @Column(name = "DEPARTMENT", nullable = false)
    private String department;
}

