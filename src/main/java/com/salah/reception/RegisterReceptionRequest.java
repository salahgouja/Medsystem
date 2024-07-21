package com.salah.reception;

import com.salah.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class RegisterReceptionRequest {
    @NotEmpty(message = "firstname is mandatory")
    @NotBlank(message = "firstname is mandatory")
    private String firstname;
    @NotEmpty(message = "lastname is mandatory")
    @NotBlank (message = "lastname is mandatory")
    private String lastname;
    @Email(message = "email not formated !")
    @NotEmpty(message = "email is mandatory")
    @NotBlank (message = "email is mandatory")
    private String email;
    @NotEmpty(message = "password is mandatory")
    @NotBlank (message = "password is mandatory")
    @Size(min= 6,message = "password should be 6 or more")
    private String password;
    private Boolean accountLocked;
    private Boolean enabled;
    private Float salary;

    private UserRole role ;

}
