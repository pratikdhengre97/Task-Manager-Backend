package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.dto.EmployeeStats;
import com.yourapp.taskmanager.dto.ProjectDto;
import com.yourapp.taskmanager.dto.TaskDto;
import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.Activity;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import com.yourapp.taskmanager.repository.UserRepository;
import com.yourapp.taskmanager.security.SecurityUtil;
import com.yourapp.taskmanager.service.TaskService;
import com.yourapp.taskmanager.service.ProjectService;
import com.yourapp.taskmanager.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final TaskService taskService;
    private final ProjectService projectService;
    private final ActivityService activityService;
    private final UserRepository userRepository;

//    @GetMapping("/{id}/tasks")
//    public List<Task> getEmployeeTasks(@PathVariable UUID id) {
//        return taskService.findTasksByEmployeeId(id);
//    }
    @GetMapping("{id}/tasks")
    public List<TaskDto> getEmployeeTasks(@PathVariable UUID id) {
        return taskService.findTasksByEmployeeId(id)
                .stream()
                .map(TaskDto::new)
                .toList();
    }

    @GetMapping("/{id}/projects")
    public List<ProjectDto> getEmployeeProjects(@PathVariable UUID id) {
        return projectService.findProjectsByEmployeeTasks(id);
    }

//    @GetMapping("/{id}/activities")
//    public List<Activity> getEmployeeActivities(@PathVariable UUID id) {
//        return activityService.findActivitiesByEmployeeId(id);
//    }
    @GetMapping("/{id}/stats")
    public EmployeeStats getEmployeeStats(@PathVariable UUID id) {
        int assignedTasks = taskService.countTasksByEmployee(id);
        int completedTasks = taskService.countTasksByEmployeeAndStatus(id, "COMPLETED");
        int pendingTasks = taskService.countTasksByEmployeeAndStatus(id, "PENDING");
        int overdueTasks = taskService.countOverdueTasksByEmployee(id);

        return new EmployeeStats(assignedTasks, completedTasks, pendingTasks, overdueTasks);
    }

    @PutMapping("/{id}/status")
    public TaskDto updateTaskStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        TaskStatus newStatus = TaskStatus.valueOf(body.get("status"));

        // get current logged-in user’s email from SecurityContext
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task updated = taskService.updateTaskStatus(id, user.getId(), newStatus);
        return new TaskDto(updated);
    }

    @GetMapping("/{id}/activities")
    public List<Activity> getEmployeeActivities(@PathVariable UUID employeeId) {
        return activityService.getActivities(employeeId);
    }
}
