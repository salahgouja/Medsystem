package com.salah.service;

import com.salah.dto.DoctorDTO;
import com.salah.dto.PatientDTO;
import com.salah.dto.ReceptionDTO;
import com.salah.dto.UserDTO;
import com.salah.entity.*;
import com.salah.exception.UserNotFoundException;
import com.salah.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserRepository userRepository;
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void addUser(UserDTO userDTO) {
        User user = new User();
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }
    public void addDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        mapToEntity(doctorDTO.user(), doctor);
        doctor.setSpecialization(doctorDTO.specialization());
        userRepository.save(doctor);
    }

    public void addPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        mapToEntity(patientDTO.user(), patient);
        patient.setMedicalRecordNumber(patientDTO.medicalRecordNumber());
        userRepository.save(patient);
    }

    public void addReception(ReceptionDTO receptionDTO) {
        Reception reception = new Reception();
        mapToEntity(receptionDTO.user(), reception);
        reception.setSalary(receptionDTO.salary());
        userRepository.save(reception);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User " +id+ " not found"));
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User " +id+ " not found"));
        return mapToDTO(user);
    }

    public List<UserDTO> getUsersByFirstname(String firstname) {
        return userRepository.findByFirstname(firstname).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<UserDTO> getUsersByLastname(String lastname) {
        return userRepository.findByLastname(lastname).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public List<UserDTO> getUsersByEmail(String email) {
        return userRepository.findByEmail(email).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public List<UserDTO> findByRole(UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPhone(),
                user.getGender(),
                user.getImage(),
                user.getTitle(),
                user.getBirthdate(),
                user.getRole(),
                user.getAccountLocked(),
                user.getEnabled(),
                user.getCreatedDate() ,
                user.getLastModifiedDate()
        );
    }

    private void mapToEntity(UserDTO userDTO, User user) {
        user.setFirstname(userDTO.firstname());
        user.setLastname(userDTO.lastname());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user.setGender(userDTO.gender());
        user.setImage(userDTO.image());
        user.setTitle(userDTO.title());
        user.setBirthdate(userDTO.birthdate());
        user.setRole(userDTO.role());

    }
    /////////////////////

    public Doctor getDoctorById(Long doctorId) {
        User user = userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor not found with id: " + doctorId));

        if (!(user instanceof Doctor)) {
            throw new UserNotFoundException("User with id " + doctorId + " is not a Doctor");
        }
        return (Doctor) user;
    }
    public Patient getPatientById(Long patientId) {
        User user = userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient not found with id: " + patientId));

        if (!(user instanceof Patient)) {
            throw new UserNotFoundException("User with id " + patientId + " is not a patient");
        }
        return (Patient) user;
    }
    public Reception getReceptionById(Long receptionId) {
        User user = userRepository.findById(receptionId)
                .orElseThrow(() -> new UserNotFoundException("Reception not found with id: " + receptionId));

        if (!(user instanceof Reception)) {
            throw new UserNotFoundException("User with id " + receptionId + " is not a reception");
        }
        return (Reception) user;
    }

    /////////////////////


    // Associate patients with a doctor
    public void associatePatients(Long doctorId, List<Long> patientIds) {
        Doctor doctor = (Doctor) userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor " + doctorId + " not found"));
        List<Patient> patients = userRepository.findAllById(patientIds).stream()
                .map(user -> (Patient) user)
                .collect(Collectors.toList());
        doctor.setPatients(patients);
        userRepository.save(doctor);
    }

    // Associate receptions with a doctor
    public void associateReceptions(Long doctorId, List<Long> receptionIds) {
        Doctor doctor = (Doctor) userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor " + doctorId + " not found"));
        List<Reception> receptions = userRepository.findAllById(receptionIds).stream()
                .map(user -> (Reception) user)
                .collect(Collectors.toList());
        doctor.setReceptions(receptions);
        userRepository.save(doctor);
    }

    // Get all patients of a doctor
    public List<Patient> getDoctorPatients(Long doctorId) {
        Doctor doctor = (Doctor) userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor " + doctorId + " not found"));
        return doctor.getPatients();
    }

    // Get all receptions of a doctor
    public List<Reception> getDoctorReceptions(Long doctorId) {
        Doctor doctor = (Doctor) userRepository.findById(doctorId)
                .orElseThrow(() -> new UserNotFoundException("Doctor " + doctorId + " not found"));
        return doctor.getReceptions();
    }

    // Get all doctors of a patient
    public List<Doctor> getPatientDoctors(Long patientId) {
        Patient patient = (Patient) userRepository.findById(patientId)
                .orElseThrow(() -> new UserNotFoundException("Patient " + patientId + " not found"));
        return patient.getDoctors();
    }

    // Get all doctors of a reception
    public List<Doctor> getReceptionDoctors(Long receptionId) {
        Reception reception = (Reception) userRepository.findById(receptionId)
                .orElseThrow(() -> new UserNotFoundException("Reception " + receptionId + " not found"));
        return reception.getDoctors();
    }
////////////
}