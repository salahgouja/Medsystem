package com.salah;

import com.salah.auth.AuthenticationService;
import com.salah.auth.RegisterRequest;
import com.salah.entity.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

/* contains these 3
@ComponentScan(basePackages = "com.salah")
@EnableAutoConfiguration
@Configuration
*/
@SpringBootApplication
@RestController
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RequestMapping
@EnableAsync
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }
    @Bean
    public CommandLineRunner commandLineRunner( AuthenticationService service) {

        return args -> {


            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(UserRole.ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var doctor = RegisterRequest
                    .builder()
                    .firstname("doctor")
                    .lastname("doctor")
                    .email("doctor@mail.com")
                    .password("password")
                    .role(UserRole.DOCTOR)
                    .build();
            System.out.println("Doctor token: " + service.register(doctor).getAccessToken());

            var reception = RegisterRequest
                    .builder()
                    .firstname("reception")
                    .lastname("reception")
                    .email("reception@mail.com")
                    .password("password")
                    .role(UserRole.RECEPTION)
                    .build();
            System.out.println("Reception token: " + service.register(reception).getAccessToken());

            var patient = RegisterRequest
                    .builder()
                    .firstname("patient")
                    .lastname("patient")
                    .email("patient@mail.com")
                    .password("password")
                    .role(UserRole.PATIENT)
                    .build();
            System.out.println("Patient token: " + service.register(patient).getAccessToken());


        };

    }
}

//netstat -ao |find /i "listening" to find and close the port
