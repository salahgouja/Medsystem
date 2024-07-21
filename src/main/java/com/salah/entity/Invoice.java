package com.salah.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.patient.Patient;
import com.salah.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonIgnoreProperties({"createdBy","lastModifiedDate","createdDate","lastModifiedBy"})

@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_sequence")
    @SequenceGenerator(name = "invoice_id_sequence", sequenceName = "invoice_id_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @JsonProperty("patient")
    private Long getPatientId(){
        return patient.getId();
    }


    @Column(name = "total_amount", nullable = false)
    private Float totalAmount;

    @Column(name = "item", nullable = false)
    private String item;

    @Column(name = "item_price", nullable = false)
    private Float itemPrice;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean paid;
}
