package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.dto.ProjectDto;
import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.ProjectStatus;
import com.yourapp.taskmanager.repository.ProjectRepository;
import com.yourapp.taskmanager.repository.UserRepository;
import com.yourapp.taskmanager.security.SecurityUtil;
import com.yourapp.taskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

//    @PostMapping
//    public Project createProject(@RequestBody Project project) {
//        String email = SecurityUtil.getCurrentUserEmail();
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        return projectService.createProject(project, user);
//    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectDto dto) {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Map DTO → Entity
        Project project = new Project();
        project.setName(dto.getName());
        project.setStatus(ProjectStatus.valueOf(dto.getStatus()));
        project.setTaskCount(dto.getTaskCount());
        project.setDeadLine(dto.getDeadline());
        project.setAssignedBy(user);
//        project.setUser(user);

        Project saved = projectService.createProject(project, user);

        // Return DTO back to frontend
        return new ProjectDto(saved);
    }

    @PostMapping("/{projectId}/members")
    public ResponseEntity<String> addMember (
            @PathVariable UUID projectId,
            @RequestParam UUID adminId,
            @RequestBody User newUser) {

        projectService.addMember(projectId, adminId, newUser);
        return ResponseEntity.ok("Member added successfully");
    }

    @GetMapping
    public List<ProjectDto> getProjects() {
        return projectService.getAllProjects();
    }
//    @PutMapping("/{id}")
//    public Project updateProject(@PathVariable UUID id, @RequestBody Project project) {
//        return projectService.updateProject(id, project);
//    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/status")
    public Project updateProject(@PathVariable UUID id, @RequestBody Map<String, String> body) {
        ProjectStatus status = ProjectStatus.valueOf(body.get("status"));
        return projectService.updateProjectStatus(id, status);
    }
    @PutMapping("/{id}")
    public ProjectDto updateProject(@PathVariable UUID id, @RequestBody ProjectDto dto) {
        Project project = projectRepository.findById(id).orElseThrow();
        project.setName(dto.getName());
        project.setTaskCount(dto.getTaskCount());
        project.setStatus(ProjectStatus.valueOf(dto.getStatus()));
        project.setDeadLine(dto.getDeadline()); // NEW
        return new ProjectDto(projectRepository.save(project));
    }

//    @GetMapping("/total-tasks")
//    public long getTotalTasks() {
//        return projectService.countTasksFromProjects();
//    }

}