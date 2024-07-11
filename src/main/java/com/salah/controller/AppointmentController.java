package com.salah.controller;

import com.salah.entity.Appointment;
import com.salah.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{appointmentId}")
    public Appointment getAppointmentById(@PathVariable Long appointmentId) {
        return appointmentService.getAppointmentById(appointmentId);
    }

    @PostMapping
    public void createAppointment(@RequestParam Long patientId, @RequestParam Long receptionId, @RequestBody Appointment appointment) {
        appointmentService.createAppointment(patientId, receptionId, appointment);
    }

    @PutMapping("/{appointmentId}")
    public void updateAppointment(@PathVariable Long appointmentId, @RequestBody Appointment updatedAppointment) {
        appointmentService.updateAppointment(appointmentId, updatedAppointment);
    }

    @DeleteMapping("/{appointmentId}")
    public void deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
    }

    @GetMapping("/reception/{receptionId}")
    public List<Appointment> getAppointmentsByReceptionId(@PathVariable Long receptionId) {
        return appointmentService.getAppointmentsByReceptionId(receptionId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatientId(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatientId(patientId);
    }
}
