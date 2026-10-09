package com.civicfix.dto;

import com.civicfix.entity.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String phone;
    private Role role;
}