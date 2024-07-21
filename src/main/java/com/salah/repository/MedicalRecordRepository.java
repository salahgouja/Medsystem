package com.salah.repository;

import com.salah.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByDoctorId(Long doctorId);

    List<MedicalRecord> findByPatientId(Long patientId);
}
