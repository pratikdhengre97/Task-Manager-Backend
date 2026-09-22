//package com.yourapp.taskmanager.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable()) // disable CSRF for testing
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/projects").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/tasks").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//    }
//}
