package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.dto.ProjectDto;
import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.ProjectMember;
import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.ProjectRole;
import com.yourapp.taskmanager.enums.ProjectStatus;
import com.yourapp.taskmanager.repository.ProjectMemberRepository;
import com.yourapp.taskmanager.repository.ProjectRepository;
import com.yourapp.taskmanager.repository.TaskRepository;
import com.yourapp.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public List<ProjectDto> findProjectsByEmployeeId(UUID employeeId) {
        List<ProjectMember> memberships = projectMemberRepository.findByUser_Id(employeeId);
        return memberships.stream()
                .map(pm -> new ProjectDto(pm.getProject()))
                .collect(Collectors.toList());
    }

    public Project createProject(Project project, User user) {
        // Save project
        Project saved = projectRepository.save(project);

        // Automatically add creator as ADMIN
        ProjectMember pm = ProjectMember.builder()
                .project(saved)
                .user(user)
                .role(ProjectRole.ADMIN)
                .build();

        projectMemberRepository.save(pm);

        return saved;
    }

    public void addMember(UUID projectId, UUID adminId, User newUser) {


        // Ensure admin rights
        projectMemberService.checkAdmin(adminId, projectId);

        // Load project
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project does not exist"));

        User existingUser = userRepository.findById(newUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save new member
        ProjectMember pm = ProjectMember.builder()
                .project(project)
                .user(existingUser)
                .role(ProjectRole.MEMBER)
                .build();

        projectMemberRepository.save(pm);
    }

    public long countProjects() {
        return projectRepository.count();
    }

    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(ProjectDto::new)
                .collect(Collectors.toList());
    }
    public long countCompletedProjects() {
        return projectRepository.countByStatus(ProjectStatus.COMPLETED);
    }

    public long countPendingProjects() {
        return projectRepository.countByStatus(ProjectStatus.PENDING);
    }

    public Project updateProject(UUID id, Project updated) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if(updated.getName() != null) {
            project.setName(updated.getName());
        }

        if(updated.getStatus() != null) {
            project.setStatus(updated.getStatus());
        }

        if(updated.getTaskCount() != 0) {
            project.setTaskCount(updated.getTaskCount());
        }
        return projectRepository.save(project);
    }
    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }

    public Project updateProjectStatus(UUID id, ProjectStatus status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setStatus(status);
        return projectRepository.save(project);
    }


    public long countTasksFromProjects() {
        return projectRepository.findAll()
                .stream()
                .mapToLong(Project::getTaskCount)
                .sum();
    }
    public List<ProjectDto> findProjectsByEmployeeTasks(UUID employeeId) {
        List<Task> tasks = taskRepository.findByAssignedTo_Id(employeeId);

        return tasks.stream()
                .map(Task::getProject)       // get the project from each task
                .distinct()                  // avoid duplicates
                .map(ProjectDto::new)        // convert to DTO
                .collect(Collectors.toList());
    }

}