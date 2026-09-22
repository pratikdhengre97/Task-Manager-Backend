package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {
}
