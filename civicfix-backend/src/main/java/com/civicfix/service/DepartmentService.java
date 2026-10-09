package com.civicfix.service;

import com.civicfix.dto.DepartmentRequest;
import com.civicfix.dto.DepartmentResponse;
import com.civicfix.entity.Department;
import com.civicfix.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse create(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("Department already exists");
        }
        Department department = new Department();
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        return toResponse(departmentRepository.save(department));
    }

    public List<DepartmentResponse> getAll() {
        return departmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    private DepartmentResponse toResponse(Department d) {
        DepartmentResponse r = new DepartmentResponse();
        r.setId(d.getId());
        r.setName(d.getName());
        r.setDescription(d.getDescription());
        return r;
    }
}