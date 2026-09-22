package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.dto.TaskDto;
import com.yourapp.taskmanager.dto.TaskRequestDto;
import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import com.yourapp.taskmanager.repository.UserRepository;
import com.yourapp.taskmanager.security.SecurityUtil;
import com.yourapp.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @PostMapping
    public TaskDto createTask(@RequestBody TaskRequestDto dto) {

        // get current logged-in user’s email from SecurityContext
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task saved = taskService.createTask(dto, user.getId());

        return new TaskDto(saved);
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

//    Update Status
//    @PutMapping("/{id}/status")
//    public Task updateTaskStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
//
//        TaskStatus newStatus = TaskStatus.valueOf(body.get("status"));
//        return taskService.updateTaskStatus(id, newStatus);
//    }

//    @PutMapping("/{id}/status")
//    public TaskDto updateTaskStatus(@PathVariable UUID id, @RequestBody Map<String, String> body) {
//        TaskStatus newStatus = TaskStatus.valueOf(body.get("status"));
//        Task updated = TaskService.updateTaskStatus(id, newStatus);
//        return new TaskDto(updated);
//
//    }

    //Get all tasks
    @GetMapping
    public List<TaskDto> getAllTasks() {
        return taskService.findAllTasks()
                .stream()
                .map(TaskDto::new)
                .toList();
    }

    //Get tasks by Employees
//    @GetMapping("/employee/{employeeId}")
//    public List<Task> getTasksByEmployee(@PathVariable UUID employeeId) {
//        return taskService.findTasksByEmployeeId(employeeId);
//    }

    @GetMapping("/employee/{employeeId}")
    public List<TaskDto> getTasksByEmployee(@PathVariable UUID employeeId) {
        return taskService.findTasksByEmployeeId(employeeId)
                .stream()
                .map(TaskDto::new)
                .toList();
    }

    //Tasks Count
    @GetMapping("/status-counts")
    public Map<TaskStatus, Long> getStatusCounts() {
        return taskService.countTasksByStatus();
    }

    @GetMapping("/overview/projects")
    public Map<String, Long> getTasksPerProject() {
        return taskService.countTasksPerProject();
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }


}