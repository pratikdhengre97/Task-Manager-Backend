//package com.yourapp.taskmanager.controller;
//
//import com.yourapp.taskmanager.entity.User;
//import com.yourapp.taskmanager.service.ProjectService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/projects/{projectId}/members")
//@RequiredArgsConstructor
//public class ProjectMemberController {
//
//    private final ProjectService projectService;
//
//    @PostMapping
//    public ResponseEntity<Void> addMember(
//            @PathVariable UUID projectId,
//            @RequestParam UUID adminId,
//            @RequestBody User newUser) {
//
//        projectService.addMember(projectId, adminId, newUser);
//        return ResponseEntity.ok().build();
//    }
//}
