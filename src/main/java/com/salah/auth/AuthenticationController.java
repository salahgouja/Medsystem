package com.salah.auth;


import com.salah.doctor.RegisterDoctorRequest;
import com.salah.patient.RegisterPatientRequest;
import com.salah.reception.RegisterReceptionRequest;
import com.salah.user.RegisterUserRequest;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService service ;

    @PostMapping("/registerUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody @Valid RegisterUserRequest request
    ) throws MessagingException {

        service.registerUser(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/registerDoctor")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> registerDoctor(
            @RequestBody @Valid RegisterDoctorRequest request
    ) throws MessagingException {

        service.registerDoctor(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/registerPatient")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> registerPatient(
            @RequestBody @Valid RegisterPatientRequest request
    ) throws MessagingException {

        service.registerPatient(request);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/registerReception")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> registerReception(
            @RequestBody @Valid RegisterReceptionRequest request
    ) throws MessagingException {

        service.registerReception(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ){
        AuthenticationResponse response = service.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }


    @PostMapping("/refresh-token")

    public void  refreshtoken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request,response);
    }







}
