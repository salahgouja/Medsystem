package com.salah.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name= "receptions")

public class Reception extends User {
    private Float salary;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate ;


    @ManyToMany(mappedBy = "receptions")
    private List<Doctor> doctors;

}
