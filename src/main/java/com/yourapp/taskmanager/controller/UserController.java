package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{id}/role")
    public User updateRole(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        return userService.updateRole(id, body.get("role"));
    }

    @PutMapping("/{id}/project")
    public User assignProject(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        return userService.assignProject(id, body.get("project"));
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable UUID id) {
        userService.removeUser(id);
    }



}
