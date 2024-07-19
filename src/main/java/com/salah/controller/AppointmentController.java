package com.salah.controller;

import com.salah.entity.Appointment;
import com.salah.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/appointments")

@PreAuthorize("hasAnyRole('ADMIN','DOCTOR','RECEPTION', 'PATIENT')")

public class AppointmentController {

    private final AppointmentService appointmentService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read', 'patient:read')")

    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{appointmentId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read', 'patient:read')")

    public Appointment getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin:create', 'doctor:create' , 'reception:create' ,'patient:create')")

    public void createAppointment(@RequestParam Long patientId, @RequestParam Long receptionId, @RequestBody Appointment appointment) {
        appointmentService.createAppointment(patientId, receptionId, appointment);
    }

    @PutMapping("/{appointmentId}")
    @PreAuthorize("hasAnyAuthority('admin:update', 'doctor:update' , 'reception:update' ,'patient:update')")

    public void updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        appointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @DeleteMapping("/{appointmentId}")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'doctor:delete','reception:delete')")

    public void deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }

    @GetMapping("/reception/{receptionId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read')")

    public List<Appointment> getAppointmentsByReceptionId(@PathVariable Long receptionId) {
        return appointmentService.getAppointmentsByReceptionId(receptionId);
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyAuthority('admin:read', 'doctor:read' , 'reception:read')")

    public List<Appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }
}
