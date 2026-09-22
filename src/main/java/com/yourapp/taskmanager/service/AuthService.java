package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    public User register(User user) {
//        return userRepository.save(user);
//    }
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.count() == 0) {
            user.setRole("ADMIN");
        } else {
            user.setRole("MEMBER");
        }
        return userRepository.save(user);
    }
}