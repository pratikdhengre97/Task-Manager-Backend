package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.ProjectMember;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.ProjectRole;
import com.yourapp.taskmanager.repository.ProjectMemberRepository;
import com.yourapp.taskmanager.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public ProjectRole getUserRole(UUID userId, UUID projectId) {
        ProjectMember pm = projectMemberRepository
                .findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("User not part of project"));

        return pm.getRole();
    }

    public void checkAdmin(UUID userId, UUID projectId) {
        ProjectRole role = getUserRole(userId, projectId);
        if (role != ProjectRole.ADMIN) {
            throw new RuntimeException("Access Denied: Admin only");
        }
    }

    public void checkMember(UUID userId, UUID projectId) {
        getUserRole(userId, projectId); // just validates existence
    }

    public void addMember(UUID projectId, UUID adminId, User newUser) {
        checkAdmin(adminId, projectId);

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));



        ProjectMember pm = ProjectMember.builder()
                .project(project)
                .user(newUser)
                .role(ProjectRole.MEMBER)
                .build();

        projectMemberRepository.save(pm);
    }



}