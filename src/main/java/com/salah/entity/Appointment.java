package com.salah.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.patient.Patient;
import com.salah.reception.Reception;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "appointments")
@JsonIgnoreProperties({"createdBy","lastModifiedDate","createdDate","lastModifiedBy"})

public class Appointment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_id_sequence")
    @SequenceGenerator(name = "appointment_id_sequence", sequenceName = "appointment_id_sequence", allocationSize = 1)

    @Column(name = "id",
            updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    @JsonProperty("patient")
    private Long getPatientId() {
        return patient.getId();
    }

    @ManyToOne
    @JoinColumn(name = "reception_id", nullable = false)
    private Reception reception;
    @JsonProperty("reception")
    private Long getReceptionId(){
        return reception.getId();

    }
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String time;


}
