package com.salah.doctor;


import com.salah.user.UserDTO;

public record DoctorDTO (
        UserDTO user,
        String specialization
) {}
