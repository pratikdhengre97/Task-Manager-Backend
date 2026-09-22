package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    // Assuming Project has a collection of members (User entities)
//    List<Project> findByMembers_Id(UUID userId);

    long countByStatus(ProjectStatus status);
}
