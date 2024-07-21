package com.salah.controller;

import com.salah.entity.MedicalRecord;
import com.salah.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/medicalRecords")
@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTION','PATIENT')")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;


    @GetMapping("/doctor/{doctorId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read' ,'patient:read' )")
    public List<MedicalRecord> getAllMedicalRecordsForDoctor(@PathVariable Long doctorId) {
        return medicalRecordService.getAllMedicalRecordsForDoctor(doctorId);
    }
    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read' ,'patient:read' )")
    public List<MedicalRecord> getAllMedicalRecordsForPatient(@PathVariable Long patientId) {
        return medicalRecordService.getAllMedicalRecordsForPatient(patientId);
    }

    @GetMapping("/{recordId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read', 'patient:read')")

    public MedicalRecord getMedicalRecord(@PathVariable Long recordId) {
        return medicalRecordService.getMedicalRecord(recordId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'doctor:create' , 'reception:create')")

    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping("/{recordId}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'doctor:update' , 'reception:update')")

    public void updateMedicalRecord(@PathVariable Long recordId, @RequestBody MedicalRecord updatedRecord) {
        medicalRecordService.updateMedicalRecord(recordId, updatedRecord);
    }

    @DeleteMapping("/{recordId}")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'doctor:delete')")

    public void deleteMedicalRecord(@PathVariable Long recordId) {
        medicalRecordService.deleteMedicalRecord(recordId);
    }
}
