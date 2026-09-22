package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.entity.UserInquiry;
import com.yourapp.taskmanager.repository.UserInquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInquiryService {
    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(UserInquiryRepository userInquiryRepository) {
        this.userInquiryRepository = userInquiryRepository;
    }

    public List<UserInquiry> getAllInquiries() {
        return userInquiryRepository.findAll();
    }

    public UserInquiry resolveInquiry(Long id) {
        UserInquiry inquiry = userInquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setResolved(true);
        return userInquiryRepository.save(inquiry);
    }

    public UserInquiry replyToInquiry(Long id, String reply) {
        UserInquiry inquiry = userInquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));

        inquiry.setReply(reply);
        return userInquiryRepository.save(inquiry);
    }

    public UserInquiry markAsUnresolved(Long id) {
        UserInquiry inquiry = userInquiryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inquiry not found"));
        inquiry.setResolved(false);
        return userInquiryRepository.save(inquiry);
    }
}
