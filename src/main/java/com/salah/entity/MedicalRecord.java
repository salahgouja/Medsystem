package com.salah.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.doctor.Doctor;
import com.salah.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties({"createdBy","lastModifiedDate","createdDate","lastModifiedBy"})

public class MedicalRecord extends BaseEntity{

    public MedicalRecord(LocalDateTime createdDate, LocalDateTime lastModifiedDate, Integer createdBy, Integer lastModifiedBy, Long id, String description, Patient patient, Doctor doctor) {
        super(createdDate, lastModifiedDate, createdBy, lastModifiedBy);
        this.id = id;
        this.description = description;
        this.patient = patient;
        this.doctor = doctor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn
    private Patient patient;
    @JsonProperty("patient")
    private Long getPatientId(){
        return patient.getId();
    }

    @Setter
    @ManyToOne
    @JoinColumn
    private Doctor doctor;
    @JsonProperty("doctor")
    public List<Long> getDoctorIds() {
        return Collections.singletonList(doctor.getId());
    }

}
