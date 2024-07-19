package com.salah.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("DOCTOR")

public class Doctor extends User {
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    private List<Task> tasks;


    @ManyToMany
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients;

    @ManyToMany
    @JoinTable(
            name = "doctor_reception",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "reception_id")
    )
    private List<Reception> receptions;


    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;
}
