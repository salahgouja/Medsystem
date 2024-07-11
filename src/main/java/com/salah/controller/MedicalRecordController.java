package com.salah.controller;

import com.salah.entity.MedicalRecord;
import com.salah.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/medicalRecords")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/doctor/{doctorId}")
    public List<MedicalRecord> getAllMedicalRecords(@PathVariable Long doctorId) {
        return medicalRecordService.getAllMedicalRecords(doctorId);
    }

    @GetMapping("/{recordId}")
    public MedicalRecord getMedicalRecord(@PathVariable Long recordId) {
        return medicalRecordService.getMedicalRecord(recordId);
    }

    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/{recordId}")
    public void updateMedicalRecord(@PathVariable Long recordId, @RequestBody MedicalRecord updatedRecord) {
        medicalRecordService.updateMedicalRecord(recordId, updatedRecord);
    }

    @DeleteMapping("/{recordId}")
    public void deleteMedicalRecord(@PathVariable Long recordId) {
        medicalRecordService.deleteMedicalRecord(recordId);
    }
}
