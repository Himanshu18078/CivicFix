package com.civicfix.config;

import com.civicfix.entity.Role;
import com.civicfix.entity.User;
import com.civicfix.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class AdminSeeder {

    @Bean
    CommandLineRunner seedAdmin(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            String email = "admin@civicfix.com";
            if (!repo.existsByEmail(email)) {
                User admin = new User();
                admin.setName("System Admin");
                admin.setEmail(email);
                admin.setPassword(encoder.encode("Admin@123"));
                admin.setPhone("0000000000");
                admin.setRole(Role.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());
                repo.save(admin);
            }
        };
    }
}