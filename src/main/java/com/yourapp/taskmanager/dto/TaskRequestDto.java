package com.yourapp.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    private String title;
    private UUID assigneeId;
    private UUID projectId;
    private LocalDate dueDate;
    private String status;
}
