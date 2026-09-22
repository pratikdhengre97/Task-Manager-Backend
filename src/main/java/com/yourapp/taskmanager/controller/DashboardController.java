package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.yourapp.taskmanager.repository.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @GetMapping("/admin")
    public Map<String, Object> getAdminStats() {

        long totalProjects = projectRepository.count();
        long totalTasks = taskRepository.count();
        long completed = taskRepository.countByStatus(TaskStatus.COMPLETED);
        long pending = taskRepository.countByStatus(TaskStatus.TODO);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProjects", totalProjects);
        stats.put("totalTasks", totalTasks);
        stats.put("completed", completed);
        stats.put("pending", pending);

        return stats;
    }

    // 🔹 MEMBER STATS (ADD HERE 👇)
    @GetMapping("/member")
    public Map<String, Object> getMemberStats(Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("Unauthorized");
        }
        System.out.println("AUTH: " + authentication); // 👈 ADD THIS
        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        long total = taskRepository.countByAssignedTo(user);
        long completed = taskRepository.countByAssignedToAndStatus(user, TaskStatus.COMPLETED);
        long inProgress = taskRepository.countByAssignedToAndStatus(user, TaskStatus.IN_PROGRESS);
        long pending = taskRepository.countByAssignedToAndStatus(user, TaskStatus.TODO);

        Map<String, Object> stats = new HashMap<>();
        stats.put("total", total);
        stats.put("completed", completed);
        stats.put("inProgress", inProgress);
        stats.put("pending", pending);

        return stats;
    }
}