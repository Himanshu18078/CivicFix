package com.civicfix.controller;

import com.civicfix.dto.DepartmentRequest;
import com.civicfix.dto.DepartmentResponse;
import com.civicfix.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // Any logged-in user can see departments
    @GetMapping("/api/departments")
    public List<DepartmentResponse> getAll() {
        return departmentService.getAll();
    }

    // Admin only: covered by the /api/admin/** rule in SecurityConfig
    @PostMapping("/api/admin/departments")
    public ResponseEntity<DepartmentResponse> create(
            @Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(departmentService.create(request));
    }
}