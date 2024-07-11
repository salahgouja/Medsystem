package com.salah.dto;

import com.salah.entity.*;

public class DTOConverter {

    public static UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
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

    public static DoctorDTO convertToDoctorDTO(Doctor doctor) {
        UserDTO userDTO = convertToDTO(doctor);
        return new DoctorDTO(userDTO, doctor.getSpecialization());
    }

    public static PatientDTO convertToPatientDTO(Patient patient) {
        UserDTO userDTO = convertToDTO(patient);
        return new PatientDTO(userDTO, patient.getMedicalRecordNumber());
    }

    public static ReceptionDTO convertToReceptionDTO(Reception reception) {
        UserDTO userDTO = convertToDTO(reception);
        return new ReceptionDTO(userDTO, reception.getSalary());
    }
}
