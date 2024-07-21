package com.salah.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salah.doctor.Doctor;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties({"createdBy","lastModifiedDate","createdDate","lastModifiedBy"})
@Entity(name ="Task" )
@Table(name= "tasks")
public class Task extends BaseEntity{
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
