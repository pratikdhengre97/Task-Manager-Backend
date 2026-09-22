package com.yourapp.taskmanager.service;

import com.yourapp.taskmanager.dto.ContactRequest;
import com.yourapp.taskmanager.entity.ContactMessage;
import com.yourapp.taskmanager.repository.ContactRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public void saveMessage(ContactRequest request) {
        ContactMessage message = new ContactMessage(
                request.getName(),
                request.getEmail(),
                request.getMessage()
        );
        contactRepository.save(message);
    }

    public List<ContactMessage> findAllMessages() {
        return contactRepository.findAll();
    }
}
