package com.salah.patient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.doctor.Doctor;
import com.salah.entity.MedicalRecord;
import com.salah.user.User;
import com.salah.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name= "patients")
@JsonIgnoreProperties({"password","firstname","lastname","email","phone","gender","image","title","birthdate","receptions","accountLocked","enabled","tokens","createdDate","lastModifiedDate", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})


public class Patient extends User {

    public Patient(String firstname, String lastname, String email, String password, Boolean accountLocked, Boolean enabled, Long medicalRecordNumber ) {
        super(firstname, lastname, email, password, accountLocked, enabled);
        this.setAccountLocked(false);
        this.setEnabled(true);
        this.medicalRecordNumber = medicalRecordNumber;
        this.setRole(UserRole.PATIENT);
    }
    private Long medicalRecordNumber;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate ;


    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;
    @JsonProperty("doctors")
    public List<Long> getDoctorIds() {
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toList());
    }



    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords;
    @JsonProperty("medicalRecords")
    public List<Long> getMedicalRecordIds() {
        return medicalRecords.stream()
                .map(MedicalRecord::getId)
                .collect(Collectors.toList());
    }

}
