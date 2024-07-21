package com.salah.service;

import com.salah.entity.MedicalRecord;
import com.salah.exception.RecordNotFoundException;
import com.salah.repository.MedicalRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getAllMedicalRecordsForDoctor(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
    }
    public List<MedicalRecord> getAllMedicalRecordsForPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
    public MedicalRecord getMedicalRecord(Long recordId) {
        return medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException("Record " + recordId + " not found"));
    }





    public void createMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordRepository.save(medicalRecord);
    }
    public void updateMedicalRecord(Long recordId, MedicalRecord updatedRecord) {
        MedicalRecord medicalRecord = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new RecordNotFoundException("Record " + recordId + " not found"));
        medicalRecord.setDescription(updatedRecord.getDescription());
        medicalRecord.setPatient(updatedRecord.getPatient());
        medicalRecord.setDoctor(updatedRecord.getDoctor());
        medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(Long recordId) {
        medicalRecordRepository.deleteById(recordId);
    }



}
