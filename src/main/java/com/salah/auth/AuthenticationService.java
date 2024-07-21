package com.salah.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.salah.doctor.RegisterDoctorRequest;
import com.salah.email.EmailService;
import com.salah.email.EmailTemplateName;
import com.salah.doctor.Doctor;
import com.salah.doctor.DoctorRepository;
import com.salah.patient.Patient;
import com.salah.patient.PatientRepository;
import com.salah.patient.RegisterPatientRequest;
import com.salah.reception.Reception;
import com.salah.reception.ReceptionRepository;
import com.salah.reception.RegisterReceptionRequest;
import com.salah.token.Token;
import com.salah.user.RegisterUserRequest;
import com.salah.user.User;
import com.salah.user.UserRole;
import com.salah.token.TokenRepository;
import com.salah.user.UserRepository;
import com.salah.service.JwtService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor


public class AuthenticationService {
    private final UserRepository userRepository ;
    private final DoctorRepository doctorRepository ;
    private final PatientRepository patientRepository ;
    private final ReceptionRepository receptionRepository ;


    private final PasswordEncoder passwordEncoder ;
    private final TokenRepository tokenRepository ;
    private final EmailService emailService ;
    private final AuthenticationManager authenticationManager ;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService ;

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    private <T extends User> AuthenticationResponse registerCommon(T user, JpaRepository<T, Long> repository) throws MessagingException {
        repository.save(user);
        sendValidationEmail(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);
        var jwtRefreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    public AuthenticationResponse registerUser(RegisterUserRequest request) throws MessagingException {
        if (request.getRole() == null) {
            request.setRole(UserRole.DOCTOR);
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .role(request.getRole())
                .build();

        return registerCommon(user, userRepository);
    }

    public AuthenticationResponse registerDoctor(RegisterDoctorRequest request) throws MessagingException {
        if (request.getRole() == null) {
            request.setRole(UserRole.DOCTOR);
        }
        Doctor doctor = new Doctor(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getAccountLocked(),
                request.getEnabled(),
                request.getSpecialization()
        );

        return registerCommon(doctor, doctorRepository);
    }

    public AuthenticationResponse registerPatient(RegisterPatientRequest request) throws MessagingException {
        if (request.getRole() == null) {
            request.setRole(UserRole.PATIENT);
        }
        Patient patient = new Patient(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getAccountLocked(),
                request.getEnabled(),
                request.getMedicalRecordNumber()
        );

        return registerCommon(patient, patientRepository);
    }

    public AuthenticationResponse registerReception(RegisterReceptionRequest request) throws MessagingException {
        if (request.getRole() == null) {
            request.setRole(UserRole.RECEPTION);
        }
        Reception reception = new Reception(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getAccountLocked(),
                request.getEnabled(),
                request.getSalary()
        );

        return registerCommon(reception, receptionRepository);
    }


    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                "Activation_account",
                newToken
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generatedToken;
                
    }

    private String generateActivationCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i=0 ;i<length;i++){
            int random = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(random));

        }
        return codeBuilder.toString() ;

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )

        );


        var claims = new HashMap<String ,Object>();
        var user =((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());

        var jwtToken = jwtService.generateToken(claims,user);
        var jwtRefreshToken = jwtService.generateRefreshToken(claims,user);

        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .refreshToken(jwtRefreshToken)
                .build() ;
    }
    @Transactional
    public void activateAccount(String token) throws MessagingException {
    Token savedToken = tokenRepository.findByToken(token).orElseThrow(()->new RuntimeException("Invalid token "));
    if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
        sendValidationEmail(savedToken.getUser());
        throw new RuntimeException("token has expired . a new token has been send to the same email");
    }
    var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(()-> new UsernameNotFoundException("user not found"));
    user.setEnabled(true);
    userRepository.save(user);

    savedToken.setValidatedAt(LocalDateTime.now());
    tokenRepository.save(savedToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {


        final String authHeader =request.getHeader(AUTHORIZATION);
        final String refreshToken;
        final String userEmail ;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){
             UserDetails user = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(refreshToken,user)){
                var accessToken = jwtService.generateToken(new HashMap<>(),user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
