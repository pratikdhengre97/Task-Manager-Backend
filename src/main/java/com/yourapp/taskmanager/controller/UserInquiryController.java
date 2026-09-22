package com.yourapp.taskmanager.controller;

import com.yourapp.taskmanager.entity.UserInquiry;
import com.yourapp.taskmanager.service.UserInquiryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inquiries")
public class UserInquiryController {
    private final UserInquiryService service;

    public UserInquiryController(UserInquiryService service) {
        this.service = service;
    }
    @GetMapping
    public List<UserInquiry> getAllInquiries() {
        return service.getAllInquiries();
    }

    @PutMapping("/{id}/resolve")
    public UserInquiry resolveInquiry(@PathVariable Long id) {
        return service.resolveInquiry(id);
    }

    @PostMapping("/{id}/reply")
    public UserInquiry replyToInquiry(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String reply = body.get("reply");
        return service.replyToInquiry(id,reply);
    }

    @PutMapping("/{id}/unresolve")
    public UserInquiry markAsUnresolved(@PathVariable Long id) {
        return service.markAsUnresolved(id);
    }
}
