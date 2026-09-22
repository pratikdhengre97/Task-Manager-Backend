package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.entity.Activity;
import com.yourapp.taskmanager.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public void logActivity(UUID employeeId, String message) {
        Activity activity = new Activity();
        activity.setEmployeeId(employeeId);
        activity.setMessage(message);
        activity.setTimestamp(LocalDateTime.now());
        activityRepository.save(activity);
    }

    public List<Activity> findActivitiesByEmployeeId(UUID employeeId) {
        return activityRepository.findByEmployeeId(employeeId);
    }
    public List<Activity> getActivities(UUID employeeId) {
        return activityRepository.findByEmployeeIdOrderByTimestampDesc(employeeId);
    }

}
