package com.yourapp.taskmanager.repository;

import com.yourapp.taskmanager.entity.UserInquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInquiryRepository extends JpaRepository<UserInquiry, Long> {
}
