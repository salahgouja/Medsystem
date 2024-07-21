package com.salah.reception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.salah.doctor.Doctor;
import com.salah.user.User;
import com.salah.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Table(name= "receptions")
@JsonIgnoreProperties({"password","firstname","lastname","email","phone","gender","image","title","birthdate","receptions","accountLocked","enabled","tokens","createdDate","lastModifiedDate", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})

public class Reception extends User {
    public Reception(String firstname, String lastname, String email, String password, Boolean accountLocked, Boolean enabled, Float salary ) {
        super(firstname, lastname, email, password, accountLocked, enabled);
        this.setAccountLocked(false);
        this.setEnabled(true);
        this.salary = salary;
        this.setRole(UserRole.RECEPTION);
    }
    private Float salary;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate ;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate ;


    @ManyToMany(mappedBy = "receptions")
    private List<Doctor> doctors;
    @JsonProperty("doctors")
    public List<Long> getDoctorIds() {
        return doctors.stream()
                .map(Doctor::getId)
                .collect(Collectors.toList());
    }


}
