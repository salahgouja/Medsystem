package com.salah.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_id_sequence")
    @SequenceGenerator(name = "invoice_id_sequence", sequenceName = "invoice_id_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

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
