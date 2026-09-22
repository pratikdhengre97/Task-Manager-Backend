package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }
    public User updateRole(UUID id, String role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }
    public User assignProject(UUID id, String project) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setProject(project);
        return userRepository.save(user);
    }
    public void removeUser(UUID id) {
        userRepository.deleteById(id);
    }
}
