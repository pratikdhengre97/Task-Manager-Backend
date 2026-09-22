package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.dto.ContactRequest;
import com.yourapp.taskmanager.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public String handleContact(@RequestBody ContactRequest request) {
        contactService.saveMessage(request);
        return "Message received successfully!";
    }
}
