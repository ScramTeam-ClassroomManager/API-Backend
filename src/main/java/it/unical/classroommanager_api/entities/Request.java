package it.unical.classroommanager_api.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "REQUESTS")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "REASON", nullable = false, length = 500)
    private String reason;

    @ManyToOne
    @JoinColumn(name = "CLASSROOM_ID", referencedColumnName = "ID", nullable = false)
    private Classroom classroom;

    @Column(name = "USER_SERIAL_NUMBER", nullable = false)
    private Integer userSerialNumber;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDate creationDate;
}

