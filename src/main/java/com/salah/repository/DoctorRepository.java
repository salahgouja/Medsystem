package com.salah.repository;

import com.salah.entity.Doctor;

import com.salah.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findByFirstname(String firstname);
    List<Doctor> findByLastname(String lastname);
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByRole(UserRole role);
}
