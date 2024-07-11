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

public class Doctor extends User {
    private String specialization;

    @OneToMany(mappedBy = "doctor")
    private List<Task> tasks;


    @ManyToMany
    private List<Patient> patients;

    @ManyToMany
    private List<Reception> receptions;


    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;
}
