package com.salah.auth;

import com.salah.email.EmailService;
import com.salah.email.EmailTemplateName;
import com.salah.entity.Token;
import com.salah.entity.User;
import com.salah.entity.UserRole;
import com.salah.repository.TokenRepository;
import com.salah.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor


public class AuthenticationService {
    private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder ;
    private final TokenRepository tokenRepository ;
    private final EmailService emailService ;

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    public AuthenticationResponse register(RegisterRequest request) throws MessagingException {

        var userRole= userRepository.findByRole(UserRole.valueOf("ADMIN")).orElseThrow(()->new IllegalStateException("role was not initialized"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .role(userRole.getRole())
                .build();

        userRepository.save(user);
        sendValidationEmail(user);

        return null;
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
}
