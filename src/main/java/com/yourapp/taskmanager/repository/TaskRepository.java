package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByProjectId(UUID projectId);

    List<Task> findByAssignedToId(UUID userId);

    long countByStatus(TaskStatus status);

    long countByAssignedTo(User user);

    long countByAssignedToAndStatus(User user, TaskStatus status);

    @Query("SELECT t.project.name, COUNT(t) FROM Task t GROUP BY t.project.name")
    List<Object[]> countTasksPerProject();

    List<Task> findByAssignedTo_Id(UUID userId);
    int countByAssignedTo_Id(UUID employeeId);
    int countByAssignedTo_IdAndStatus(UUID employeeId, TaskStatus status);
    int countByAssignedTo_IdAndDueDateBeforeAndStatusNot(UUID employeeId, LocalDate date, TaskStatus status);

}
