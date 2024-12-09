package it.unical.classroommanager_api.entities;

import it.unical.classroommanager_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(name = "ADMIN_RESPONSE", length = 1000)
    private String adminResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @Column(name = "REQUEST_DATE", nullable = false)
    private LocalDate requestDate;

    @Column(name = "START_HOUR", nullable = false)
    private LocalTime startHour;

    @Column(name = "END_HOUR", nullable = false)
    private LocalTime endHour;
}

