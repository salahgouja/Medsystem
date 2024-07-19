package com.salah.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.salah.entity.Permisson.*;


@Getter
@RequiredArgsConstructor

public enum UserRole {


    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,

                    DOCTOR_READ,
                    DOCTOR_UPDATE,
                    DOCTOR_CREATE,
                    DOCTOR_DELETE,

                    PATIENT_READ,
                    PATIENT_UPDATE,
                    PATIENT_CREATE,
                    PATIENT_DELETE,

                    RECEPTION_READ,
                    RECEPTION_UPDATE,
                    RECEPTION_CREATE,
                    RECEPTION_DELETE
            )
    ),


    DOCTOR(
            Set.of(

            DOCTOR_READ,
            DOCTOR_UPDATE,
            DOCTOR_CREATE,
            DOCTOR_DELETE,

            RECEPTION_READ,
            RECEPTION_UPDATE,
            RECEPTION_CREATE,
            RECEPTION_DELETE,

            PATIENT_READ,
            PATIENT_UPDATE,
            PATIENT_CREATE,
            PATIENT_DELETE
            )
    ),


    PATIENT(
            Set.of(

            PATIENT_READ,
            PATIENT_UPDATE,
            PATIENT_CREATE,
            PATIENT_DELETE
            )
    ),


    RECEPTION(
            Set.of(

            RECEPTION_READ,
            RECEPTION_UPDATE,
            RECEPTION_CREATE,
            RECEPTION_DELETE,

            PATIENT_READ,
            PATIENT_UPDATE,
            PATIENT_CREATE,
            PATIENT_DELETE
            )
    )

    ;


    private final Set<Permisson> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
