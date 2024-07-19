package com.salah.service;

import com.salah.entity.Appointment;
import com.salah.entity.User;
import com.salah.exception.RecordNotFoundException;
import com.salah.repository.AppointmentRepository;
import com.salah.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RecordNotFoundException("Appointment " + appointmentId + " not found"));
    }

    public void createAppointment(Long patientId, Long receptionId, Appointment appointment) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RecordNotFoundException("Patient " + patientId + " not found"));
        User reception = userRepository.findById(receptionId)
                .orElseThrow(() -> new RecordNotFoundException("Reception " + receptionId + " not found"));
        appointment.setPatient(patient);
        appointment.setReception(reception);
        appointmentRepository.save(appointment);
    }

    public void updateAppointment(Long appointmentId, Appointment updatedAppointment) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RecordNotFoundException("Appointment " + appointmentId + " not found"));
        appointment.setDate(updatedAppointment.getDate());
        appointment.setTime(updatedAppointment.getTime());
        appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    public List<Appointment> getAppointmentsByReceptionId(Long receptionId) {
        return appointmentRepository.findByReceptionId(receptionId);
    }

    public List<Appointment> getAppointmentsByPatientId(Long patientId) {
        return appointmentRepository.findByPatientId(patientId);
    }

}
