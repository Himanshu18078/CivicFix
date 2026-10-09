package com.civicfix.controller;

import com.civicfix.dto.CategoryRequest;
import com.civicfix.dto.CategoryResponse;
import com.civicfix.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Any logged-in user
    @GetMapping("/api/categories")
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    // Admin only (covered by /api/admin/** in SecurityConfig)
    @PostMapping("/api/admin/categories")
    public ResponseEntity<CategoryResponse> create(
            @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.create(request));
    }
}