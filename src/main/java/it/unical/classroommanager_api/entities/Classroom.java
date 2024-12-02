package it.unical.classroommanager_api.entities;

import it.unical.classroommanager_api.enums.ClassroomType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CLASSROOMS")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "CUBE_NUMBER", referencedColumnName = "NUMBER", nullable = false)
    private Cube cube;

    @Column(name = "FLOOR", nullable = false)
    private int floor;

    @Column(name = "CAPABILITY", nullable = false)
    private int capability;

    @Column(name = "NUM_SOCKET")
    private int numSocket;

    @Column(name = "PROJECTOR")
    private boolean projector;

    @Column(name = "AVAILABLE")
    private boolean available;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private ClassroomType type;
}
