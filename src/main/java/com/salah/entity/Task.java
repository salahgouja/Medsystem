package com.salah.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Entity(name ="Task" )
@Table(name= "tasks")
public class Task {

    public Task(){
        this.createdAt =new Date();
    }

    @Id
    @SequenceGenerator(
            name = "task_id_sequence",
            sequenceName = "task_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "task_id_sequence")

    @Column(name = "id",
            updatable = false)
    private Long id;
    @Column(name = "name",
            nullable = false,
            columnDefinition = "Text")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "createdAt",
            nullable = false
    )
    private Date createdAt;
    @Column(name = "price",
            nullable = false
    )
    private Float price ;
    @Column(name = "status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

}
