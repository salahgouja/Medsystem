package com.salah.repository;

import com.salah.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByDoctorId(Long doctorId);
    List<MedicalRecord> findByPatientId(Long patientId);
}
