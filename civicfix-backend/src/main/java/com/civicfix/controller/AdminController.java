package com.civicfix.controller;

import com.civicfix.dto.CreateUserRequest;
import com.civicfix.dto.UpdateStatusRequest;
import com.civicfix.dto.UpdateUserRequest;
import com.civicfix.dto.UserResponse;
import com.civicfix.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public List<UserResponse> getUsers() {
        return adminService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createUser(request));
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @RequestBody UpdateUserRequest request,
                                   Authentication auth) {
        return adminService.updateUser(id, request, auth.getName());
    }

    @PutMapping("/users/{id}/status")
    public UserResponse setStatus(@PathVariable Long id,
                                  @Valid @RequestBody UpdateStatusRequest request,
                                  Authentication auth) {
        return adminService.setEnabled(id, request.getEnabled(), auth.getName());
    }
}