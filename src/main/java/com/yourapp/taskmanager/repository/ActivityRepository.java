package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findByEmployeeId(UUID employeeId);

    List<Activity> findByEmployeeIdOrderByTimestampDesc(UUID employeeId);
}
