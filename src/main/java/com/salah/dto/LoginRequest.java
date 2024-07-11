package com.salah.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

public class LoginRequest {
    private String username;
    private String password;
}
