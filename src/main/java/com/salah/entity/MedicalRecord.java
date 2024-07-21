package com.salah.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.doctor.Doctor;
import com.salah.patient.Patient;
import jakarta.persistence.*;
import lombok.*;
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
