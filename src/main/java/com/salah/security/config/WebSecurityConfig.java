package com.salah.security.config;

import com.salah.security.config.filter.JwtAuthFilter;
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

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {


    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                                req -> req.requestMatchers(
                                        "/api/v1/auth/**"
                                        ,"/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html")
                                .permitAll()

                                .requestMatchers("/api/v1/users/admin/**").hasAnyRole("ADMIN")
                                .requestMatchers("/api/v1/users/doctor/**").hasAnyRole("DOCTOR")
                                .requestMatchers("/api/v1/users/patient/**").hasAnyRole("PATIENT")
                                .requestMatchers("/api/v1/users/reception/**").hasAnyRole("RECEPTION")
                                .requestMatchers("/api/v1/users/login/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic(withDefaults())
                .sessionManagement(session ->session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
    }
}
