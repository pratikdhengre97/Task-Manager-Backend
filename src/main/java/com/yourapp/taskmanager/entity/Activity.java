package com.yourapp.taskmanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
public class Activity {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID employeeId;
    private String message;
    private LocalDateTime timestamp;
}
