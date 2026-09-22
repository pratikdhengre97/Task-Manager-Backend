package com.yourapp.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeStats {
    private int assignedTasks;
    private int completedTasks;
    private int pendingTasks;
    private int overdueTasks;
}
