package com.salah.security.config;

import com.salah.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.salah.entity.Permisson.*;
import static com.salah.entity.UserRole.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                                req -> req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()

                                        .requestMatchers("/api/v1/invoices/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name(), PATIENT.name())
                                        .requestMatchers("/api/v1/medicalRecords/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name(), PATIENT.name())

                                        .requestMatchers("/api/v1/users/patients/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name(), PATIENT.name())
                                        .requestMatchers(GET, "/api/v1/users/patients/{patientId}/doctors").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name(), RECEPTION_READ.name(),PATIENT_READ.name())

                                        .requestMatchers("/api/v1/users/patient/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name(), PATIENT.name())
                                        .requestMatchers(GET, "/api/v1/users/patient/**").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name(), RECEPTION_READ.name(),PATIENT_READ.name())
                                        .requestMatchers(POST, "/api/v1/users/patient/**").hasAnyAuthority(ADMIN_CREATE.name(), DOCTOR_CREATE.name(), RECEPTION_CREATE.name(), PATIENT_CREATE.name())
                                        .requestMatchers(PUT, "/api/v1/users/patient/**").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name(), RECEPTION_UPDATE.name(), PATIENT_UPDATE.name() )
                                        .requestMatchers(DELETE, "/api/v1/users/patient/**").hasAnyAuthority(ADMIN_DELETE.name(), DOCTOR_DELETE.name(), RECEPTION_DELETE.name(), PATIENT_DELETE.name())

                                        .requestMatchers("/api/v1/users/receptions/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name())
                                        .requestMatchers(GET, "/api/v1/users/receptions/**").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name(), RECEPTION_READ.name())
                                        .requestMatchers(GET, "/api/v1/users/receptions/{receptionId}/doctors").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name(), RECEPTION_READ.name())

                                        .requestMatchers("/api/v1/users/reception/**").hasAnyRole(ADMIN.name(), DOCTOR.name(), RECEPTION.name())
                                        .requestMatchers(GET, "/api/v1/users/reception/**").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name(), RECEPTION_READ.name())
                                        .requestMatchers(POST, "/api/v1/users/reception/**").hasAnyAuthority(ADMIN_CREATE.name(), DOCTOR_CREATE.name(), RECEPTION_CREATE.name())
                                        .requestMatchers(PUT, "/api/v1/users/reception/**").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name(), RECEPTION_UPDATE.name())
                                        .requestMatchers(DELETE, "/api/v1/users/reception/**").hasAnyAuthority(ADMIN_DELETE.name(), DOCTOR_DELETE.name(), RECEPTION_DELETE.name())


                                        .requestMatchers("/api/v1/users/doctors/**").hasAnyRole(ADMIN.name(), DOCTOR.name())
                                        .requestMatchers(GET, "/api/v1/users/doctors/{doctorId}/patients").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name())
                                        .requestMatchers(GET, "/api/v1/users/doctors/{doctorId}/receptions").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name())
                                        .requestMatchers(PUT, "/api/v1/users/doctors/{doctorId}/patients").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name())
                                        .requestMatchers(PUT, "/api/v1/users/doctors/{doctorId}/receptions").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name())

                                        .requestMatchers("/api/v1/users/doctor/**").hasAnyRole(ADMIN.name(), DOCTOR.name())
                                        .requestMatchers(GET, "/api/v1/users/doctor/**").hasAnyAuthority(ADMIN_READ.name(), DOCTOR_READ.name())
                                        .requestMatchers(POST, "/api/v1/users/doctor/**").hasAnyAuthority(ADMIN_CREATE.name())
                                        .requestMatchers(PUT, "/api/v1/users/doctor/**").hasAnyAuthority(ADMIN_UPDATE.name(), DOCTOR_UPDATE.name())
                                        .requestMatchers(DELETE, "/api/v1/users/doctor/**").hasAnyAuthority(ADMIN_DELETE.name())

                                        .requestMatchers("/api/v1/users/admin/**").hasAnyRole(ADMIN.name())
                                        .requestMatchers(GET, "/api/v1/users/admin/**").hasAnyAuthority(ADMIN_READ.name())
                                        .requestMatchers(POST, "/api/v1/users/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                                        .requestMatchers(PUT, "/api/v1/users/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                        .requestMatchers(DELETE, "/api/v1/users/admin/**").hasAnyAuthority(ADMIN_DELETE.name())

                                        .requestMatchers("/api/v1/users/{userId}").hasAnyRole(ADMIN.name())
                                        .requestMatchers(DELETE, "/api/v1/users/{userId}").hasAnyAuthority(ADMIN_DELETE.name())

                                .anyRequest()
                                .authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(session ->session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class)
                ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }
}
