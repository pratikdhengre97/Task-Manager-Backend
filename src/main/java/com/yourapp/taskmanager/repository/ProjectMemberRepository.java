package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {

    Optional<ProjectMember> findByUserIdAndProjectId(UUID userId, UUID projectId);

//    List<ProjectMember> findByUserId(UUID userId);
        List<ProjectMember> findByUser_Id(UUID userId);
}
