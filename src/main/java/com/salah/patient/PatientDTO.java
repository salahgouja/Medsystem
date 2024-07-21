package com.salah.patient;

import com.salah.user.UserDTO;

public record PatientDTO(
        UserDTO user,
         Long medicalRecordNumber
) {}
