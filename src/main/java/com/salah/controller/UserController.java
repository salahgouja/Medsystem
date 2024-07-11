package com.salah.controller;

import com.salah.dto.DoctorDTO;
import com.salah.dto.PatientDTO;
import com.salah.dto.ReceptionDTO;
import com.salah.dto.UserDTO;
import com.salah.entity.Doctor;
import com.salah.entity.Patient;
import com.salah.entity.Reception;
import com.salah.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/admin")
    public void addAdmin(@RequestBody @Valid UserDTO userDTO) {
        userService.addUser(userDTO);
    }

    @PostMapping("/doctor")
    public void addDoctor(@RequestBody @Valid DoctorDTO doctorDTO) {
        userService.addDoctor(doctorDTO);
    }

    @PostMapping("/patient")
    public void addPatient(@RequestBody @Valid PatientDTO patientDTO) {
        userService.addPatient(patientDTO);
    }

    @PostMapping("/reception")
    public void addReception(@RequestBody @Valid ReceptionDTO receptionDTO) {
        userService.addReception(receptionDTO);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("{userId}")
    public void updateUser(@PathVariable("userId") Long id, @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(id, userDTO);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/firstname/{firstname}")
    public List<UserDTO> getUsersByFirstname(@PathVariable String firstname) {
        return userService.getUsersByFirstname(firstname);
    }

    @GetMapping("/lastname/{lastname}")
    public List<UserDTO> getUsersByLastname(@PathVariable String lastname) {
        return userService.getUsersByLastname(lastname);
    }

    @GetMapping("/email/{email}")
    public List<UserDTO> getUsersByEmail(@PathVariable String email) {
        return userService.getUsersByEmail(email);
    }

    @GetMapping("/doctor/{doctorId}")
    public Doctor getDoctorById(@PathVariable Long doctorId) {
        return userService.getDoctorById(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public Patient getPatientById(@PathVariable Long patientId) {
        return userService.getPatientById(patientId);
    }

    @GetMapping("/reception/{receptionId}")
    public Reception getReceptionById(@PathVariable Long receptionId) {
        return userService.getReceptionById(receptionId);
    }

    /////////////////////

    @PutMapping("/doctors/{doctorId}/patients")
    public void associatePatients(@PathVariable Long doctorId, @RequestBody Map<String, List<Long>> request) {
        List<Long> patientIds = request.get("patientIds");
        userService.associatePatients(doctorId, patientIds);
    }

    @PutMapping("/doctors/{doctorId}/receptions")
    public void associateReceptions(@PathVariable Long doctorId, @RequestBody Map<String, List<Long>> request) {
        List<Long> receptionIds = request.get("receptionIds");
        userService.associateReceptions(doctorId, receptionIds);
    }

    @GetMapping("/doctors/{doctorId}/patients")
    public List<Patient> getDoctorPatients(@PathVariable Long doctorId) {
        return userService.getDoctorPatients(doctorId);
    }

    @GetMapping("/doctors/{doctorId}/receptions")
    public List<Reception> getDoctorReceptions(@PathVariable Long doctorId) {
        return userService.getDoctorReceptions(doctorId);
    }

    @GetMapping("/patients/{patientId}/doctors")
    public List<Doctor> getPatientDoctors(@PathVariable Long patientId) {
        return userService.getPatientDoctors(patientId);
    }

    @GetMapping("/receptions/{receptionId}/doctors")
    public List<Doctor> getReceptionDoctors(@PathVariable Long receptionId) {
        return userService.getReceptionDoctors(receptionId);
    }
//////////////////



}
