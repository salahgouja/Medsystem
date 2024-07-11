package com.salah.service;

import com.salah.entity.MedicalRecord;
import com.salah.exception.RecordNotFoundException;
import com.salah.repository.MedicalRecordRepository;
import com.salah.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final UserRepository userRepository;

    public List<MedicalRecord> getAllMedicalRecords(Long doctorId) {
        return medicalRecordRepository.findByDoctorId(doctorId);
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
        medicalRecordRepository.save(medicalRecord);
    }

    public void deleteMedicalRecord(Long recordId) {
        medicalRecordRepository.deleteById(recordId);
    }
}
