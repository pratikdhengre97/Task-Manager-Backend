package com.yourapp.taskmanager.dto;

import com.yourapp.taskmanager.entity.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {
    private UUID id;
    private String name;
    private int taskCount;
    private String status;
    private LocalDate deadline;
    private String assignedBy;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.taskCount = project.getTaskCount();
        this.status = project.getStatus().name();
        this.deadline = project.getDeadLine();
        this.assignedBy = project.getAssignedBy() != null
                ? project.getAssignedBy().getName() // or getEmail()
                : null;
    }

}
