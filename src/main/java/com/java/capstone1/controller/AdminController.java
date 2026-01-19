package com.java.capstone1.controller;

import com.java.capstone1.dto.UserRequest;
import com.java.capstone1.model.User;
import com.java.capstone1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {


    private final UserService userService;


    @PostMapping("/create")
    public User create(@RequestBody UserRequest req) {
        return userService.createUser(req);
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> show() {
        return userService.show();
    }

    @PutMapping("/users/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public User status(@PathVariable String id, @RequestParam boolean active) {
        return userService.updateStatus(id, active);
    }
}
