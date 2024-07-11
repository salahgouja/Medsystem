package com.salah.repository;

import com.salah.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    List<Appointment> findByReceptionId(Long receptionId);
    List<Appointment> findByPatientId(Long patientId);
}
