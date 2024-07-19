package com.salah.dto;

import com.salah.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Date;
public record UserDTO(
        Long id,
        @NotBlank String firstname,
        @NotBlank String lastname,
        @Email String email,
        @Nullable String phone,
        String gender,
        String image,
        String title,
        Date birthdate,
        UserRole role,
        Boolean accountLocked,
        Boolean enable,
        LocalDateTime createdDate ,
        LocalDateTime lastModifiedDate

        ) {}
