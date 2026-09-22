package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.dto.ProjectDto;
import com.yourapp.taskmanager.dto.TaskRequestDto;
import com.yourapp.taskmanager.entity.Project;
import com.yourapp.taskmanager.entity.Task;
import com.yourapp.taskmanager.entity.User;
import com.yourapp.taskmanager.enums.TaskStatus;
import com.yourapp.taskmanager.repository.ProjectRepository;
import com.yourapp.taskmanager.repository.TaskRepository;
import com.yourapp.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectMemberService projectMemberService;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ActivityService activityService;

    public Task createTask(TaskRequestDto dto, UUID userId) {
        projectMemberService.checkAdmin(userId, dto.getProjectId());

        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDueDate(dto.getDueDate());
        task.setStatus(TaskStatus.valueOf(dto.getStatus()));

        User assignee = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("Assignee not found"));
        task.setAssignedTo(assignee);

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
        task.setProject(project);

        User admin = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        task.setAssignedBy(admin.getName());

        Task saved = taskRepository.save(task);
        project.setTaskCount(project.getTaskCount() + 1);
        projectRepository.save(project);

        //Log Activity for employee
        activityService.logActivity(
                assignee.getId(),
                "Admin " + admin.getName() + " assigned you task: " + task.getTitle()
        );

        return saved;
    }

    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        Project project = task.getProject();
        taskRepository.delete(task);

        project.setTaskCount(project.getTaskCount() - 1);
        projectRepository.save(project);
    }
    public Task updateTaskStatus(UUID taskId, UUID userId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // If ADMIN → allow
        try {
            projectMemberService.checkAdmin(userId, task.getProject().getId());
        } catch (Exception e) {

            // If MEMBER → only if assigned
            if (!task.getAssignedTo().getId().equals(userId)) {
                throw new RuntimeException("Access Denied");
            }
        }

        task.setStatus(status);
        return taskRepository.save(task);
    }
    public List<Task> findTasksByEmployeeId(UUID employeeId) {
        return taskRepository.findByAssignedToId(employeeId);
    }

    public long countTasks() {
        return taskRepository.count();
    }

    public long countCompletedTasks() {
        return taskRepository.countByStatus(TaskStatus.COMPLETED);
    }

    public long countPendingTasks() {
        return taskRepository.countByStatus(TaskStatus.PENDING);
    }

//    public List<Task> findTasksByEmployeeId(UUID employeeId) {
//        return taskRepository.findByEmployeeId(employeeId);
//    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

//    public Task updateTaskStatus(UUID taskId, TaskStatus status) {
//        Task task = taskRepository.findById(taskId)
//                .orElseThrow(() -> new RuntimeException("Task not found"));
//        task.setStatus(status);
//        return taskRepository.save(task);
//    }

    public Map<TaskStatus , Long> countTasksByStatus() {
        return taskRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Task::getStatus, Collectors.counting()));
    }


    public Map<String, Long> countTasksPerProject() {
        return taskRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        task -> task.getProject().getName(), // group by project name
                        Collectors.counting()                // count tasks per project

                ));
    }

    public int countTasksByEmployee(UUID employeeId) {
        return (int) taskRepository.countByAssignedTo_Id(employeeId);
    }
    public int countTasksByEmployeeAndStatus(UUID employeeId, String status) {
        return taskRepository.countByAssignedTo_IdAndStatus(employeeId, TaskStatus.valueOf(status));
    }
    public int countOverdueTasksByEmployee(UUID employeeId) {
        return taskRepository.countByAssignedTo_IdAndDueDateBeforeAndStatusNot(
                employeeId, LocalDate.now(), TaskStatus.COMPLETED
        );
    }
    public List<ProjectDto> findProjectsByEmployeeTasks(UUID employeeId) {
        List<Task> tasks = taskRepository.findByAssignedTo_Id(employeeId);

        return tasks.stream()
                .map(Task::getProject)       // get the project from each task
                .distinct()                  // avoid duplicates
                .map(ProjectDto::new)        // convert to DTO
                .collect(Collectors.toList());
    }

}