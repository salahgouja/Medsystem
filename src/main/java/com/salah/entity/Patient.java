package com.salah.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name= "patients")

public class Patient extends User {
    private Long medicalRecordNumber;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate ;


    @ManyToMany(mappedBy = "patients")
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecord> medicalRecords;
}
