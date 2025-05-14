package com.arun.immanuel.jobtracker.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.arun.immanuel.jobtracker.filter.JwtAuthFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

        @Autowired
        private CustomAuthEntryPoint authEntryPoint;
        @Value("${PBK_SECRET_KEY}")
        private String PBKDF2_SECRET;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new Pbkdf2PasswordEncoder(PBKDF2_SECRET, 16, 185000,
                                SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
                return http
                                .cors(cors -> cors
                                                .configurationSource(request -> {
                                                        CorsConfiguration config = new CorsConfiguration();
                                                        config.setAllowedOrigins(List.of("http://localhost:4200",
                                                                        "http://localhost",
                                                                        "http://localhost:8080",
                                                                        "http://arunimmanuel.duckdns.org"));
                                                        config.setAllowedMethods(List.of(HttpMethod.GET.name(),
                                                                        HttpMethod.POST.name(), HttpMethod.PUT.name(),
                                                                        HttpMethod.DELETE.name()));
                                                        config.setAllowedHeaders(List.of("*"));
                                                        config.setAllowCredentials(true);
                                                        return config;
                                                }))
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/login",
                                                                "/api/auth/register",
                                                                "/api/health")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(authEntryPoint))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
        }
}
