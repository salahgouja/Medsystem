package com.salah.doctor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.entity.MedicalRecord;
import com.salah.patient.Patient;
import com.salah.reception.Reception;
import com.salah.entity.Task;
import com.salah.user.User;
import com.salah.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("DOCTOR")
@JsonIgnoreProperties({"password","firstname","lastname","email","phone","gender","image","title","birthdate","receptions","accountLocked","enabled","tokens","createdDate","lastModifiedDate", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})

public class Doctor extends User {

    public Doctor(String firstname, String lastname, String email, String password, Boolean accountLocked, Boolean enabled, String specialization ) {
        super(firstname, lastname, email, password, accountLocked, enabled);
        this.setAccountLocked(false);
        this.setEnabled(true);
        this.specialization = specialization;
        this.setRole(UserRole.DOCTOR);
    }


    private String specialization;

    @OneToMany(mappedBy = "doctor")
    private List<Task> tasks;
    @JsonProperty("tasks")
    public List<Long> getTaskIds() {
        return tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());
    }

    @ManyToMany
    @JoinTable(
            name = "doctor_patient",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients;
    @JsonProperty("patients")
    public List<Long> getPatientIds() {
        return patients.stream()
                .map(Patient::getId)
                .collect(Collectors.toList());
    }

    @ManyToMany
    @JoinTable(
            name = "doctor_reception",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "reception_id")
    )
    private List<Reception> receptions;

    @JsonProperty("reception")
    private List<Long> getReceptionIds(){
        return receptions.stream()
                .map(Reception::getId)
                .collect(Collectors.toList());
    }


    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;

    @JsonProperty("medicalRecords")
    public List<Long> getMedicalRecordIds() {
        return medicalRecords.stream()
                .map(MedicalRecord::getId)
                .collect(Collectors.toList());
    }
}
