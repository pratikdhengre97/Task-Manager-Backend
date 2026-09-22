package com.yourapp.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yourapp.taskmanager.dto.ContactRequest;
import com.yourapp.taskmanager.enums.TaskPriority;
import com.yourapp.taskmanager.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TODO;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.MEDIUM;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonBackReference
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Column(name="assigned_by")
    private String assignedBy;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}