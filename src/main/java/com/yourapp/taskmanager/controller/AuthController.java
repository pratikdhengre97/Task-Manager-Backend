package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.repository.UserRepository;
import com.yourapp.taskmanager.security.JwtUtil;
import com.yourapp.taskmanager.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public User register(@RequestBody User user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        var existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


//        String token = jwtUtil.generateToken(existingUser.getEmail(), existingUser.getRole());
        String token = jwtUtil.generateToken(existingUser);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", existingUser.getRole());
        response.put("userId", existingUser.getId());

        return response;
    }
}