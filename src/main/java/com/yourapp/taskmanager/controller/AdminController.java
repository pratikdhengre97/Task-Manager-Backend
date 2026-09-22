package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.dto.ProjectDto;
import com.yourapp.taskmanager.entity.ContactMessage;
import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import com.yourapp.taskmanager.service.ContactService;
import com.yourapp.taskmanager.service.ProjectService;
import com.yourapp.taskmanager.service.TaskService;
import com.yourapp.taskmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;
    private final ContactService contactService;

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        return Map.of(
                "totalProjects", projectService.countProjects(),
                "totalTasks", taskService.countTasks(),
                "totalTasks", projectService.countTasksFromProjects(),
                "completedProjects", projectService.countCompletedProjects(),
                "pendingProjects", projectService.countPendingProjects()
        );
    }

    @GetMapping("/projects")
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

//    @GetMapping("/users")
//    public List<User> getAlLUsers() {
//        return userService.findAllUsers();
//    }


    @GetMapping("/messages")
    public List<ContactMessage> getAllMessages() {
        return contactService.findAllMessages();
    }

    public Map<TaskStatus, Long> getTasksByStatus() {
        return taskService.countTasksByStatus();
    }
}
