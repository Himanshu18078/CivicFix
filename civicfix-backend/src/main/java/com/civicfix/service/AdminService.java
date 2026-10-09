package com.civicfix.service;

import com.civicfix.dto.CreateUserRequest;
import com.civicfix.dto.UpdateUserRequest;
import com.civicfix.dto.UserResponse;
import com.civicfix.entity.Role;
import com.civicfix.entity.User;
import com.civicfix.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    public UserResponse createUser(CreateUserRequest request) {
        if (request.getRole() != Role.AUTHORITY && request.getRole() != Role.WORKER) {
            throw new IllegalArgumentException("Admin can only create AUTHORITY or WORKER accounts");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        return toResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request, String adminEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());

        if (request.getRole() != null) {
            if (user.getEmail().equals(adminEmail)) {
                throw new IllegalArgumentException("You cannot change your own role");
            }
            user.setRole(request.getRole());
        }
        return toResponse(userRepository.save(user));
    }

    public UserResponse setEnabled(Long id, boolean enabled, String adminEmail) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getEmail().equals(adminEmail)) {
            throw new IllegalArgumentException("You cannot disable your own account");
        }
        user.setEnabled(enabled);
        user.setCreatedAt(LocalDateTime.now());
        return toResponse(userRepository.save(user));
    }

    private UserResponse toResponse(User user) {
        UserResponse r = new UserResponse();
        r.setId(user.getId());
        r.setName(user.getName());
        r.setEmail(user.getEmail());
        r.setPhone(user.getPhone());
        r.setRole(user.getRole());
        r.setCreatedAt(user.getCreatedAt());
        return r;
    }
}