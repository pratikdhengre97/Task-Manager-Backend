package com.yourapp.taskmanager.entity;

import com.yourapp.taskmanager.enums.ProjectRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "project_members",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "project_id"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProjectMember {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectRole role;

    private LocalDateTime joinedAt;

    @PrePersist
    public void prePersist() {
        this.joinedAt = LocalDateTime.now();
    }
}