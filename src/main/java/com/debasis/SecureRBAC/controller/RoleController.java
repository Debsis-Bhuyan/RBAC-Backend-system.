package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.model.Role;
import com.debasis.SecureRBAC.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    // Endpoint to create a new role
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        if (roleRepository.findByName(role.getName()) != null) {
            return ResponseEntity.badRequest().body("Error: Role already exists!");
        }

        roleRepository.save(role);
        return ResponseEntity.ok("Role created successfully!");
    }
}
