package com.yourapp.taskmanager.dto;

import com.yourapp.taskmanager.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private String title;
    private String assigneeName;
    private String projectName;
    private String dueDate;
    private String status;
    private String assignedBy;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.assigneeName = task.getAssignedTo() != null ? task.getAssignedTo().getName() : null; // or email
        this.projectName = task.getProject() != null ? task.getProject().getName() : null;
        this.dueDate = task.getDueDate() != null ? task.getDueDate().toString() : null;
        this.status = task.getStatus().name();
        this.assignedBy = task.getAssignedBy();
    }
}

