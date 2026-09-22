package com.yourapp.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.yourapp.taskmanager.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status = ProjectStatus.PENDING;

    @Column(nullable = false)
    private String name;

    private String description;

    private int taskCount;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
 
    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<Task> tasks;

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    @Column(name = "deadline")
    private LocalDate deadLine;

    @ManyToOne
    @JoinColumn(name = "assigned_by_id")
    private User assignedBy;



}