package com.salah.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "appointments")
public class Appointment  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_id_sequence")
    @SequenceGenerator(name = "appointment_id_sequence", sequenceName = "appointment_id_sequence", allocationSize = 1)

    @Column(name = "id",
            updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "reception_id", nullable = false)
    private User reception;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String time;


}
