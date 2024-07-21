package com.salah;

import com.salah.auth.AuthenticationService;
import com.salah.doctor.RegisterDoctorRequest;
import com.salah.patient.RegisterPatientRequest;
import com.salah.reception.RegisterReceptionRequest;
import com.salah.user.RegisterUserRequest;
import com.salah.user.UserRole;
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


            var admin = RegisterUserRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(UserRole.ADMIN)
                    .build();
            System.out.println("Admin token: " + service.registerUser(admin).getAccessToken());

            var doctor = RegisterDoctorRequest
                    .builder()
                    .firstname("doctor")
                    .lastname("doctor")
                    .email("doctor@mail.com")
                    .password("password")
                    .role(UserRole.DOCTOR)
                    .specialization("dentiste")
                    .build();
            System.out.println("Doctor token: " + service.registerDoctor(doctor).getAccessToken());

            var reception = RegisterReceptionRequest
                    .builder()
                    .firstname("reception")
                    .lastname("reception")
                    .email("reception@mail.com")
                    .password("password")
                    .role(UserRole.RECEPTION)
                    .salary(3500.0F)
                    .build();
            System.out.println("Reception token: " + service.registerReception(reception).getAccessToken());

            var patient = RegisterPatientRequest
                    .builder()
                    .firstname("patient")
                    .lastname("patient")
                    .email("patient@mail.com")
                    .password("password")
                    .role(UserRole.PATIENT)
                    .medicalRecordNumber(111L)
                    .build();
            System.out.println("Patient token: " + service.registerPatient(patient).getAccessToken());


        };

    }
}

//netstat -a -o -n
