package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.model.Role;
import com.debasis.SecureRBAC.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/role")
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        try {
            if (roleRepository.findByName(role.getName()) != null) {
                return ResponseEntity.badRequest().body("Error: Role '" + role.getName() + "' already exists!");
            }
            roleRepository.save(role);
            return ResponseEntity.ok("Role '" + role.getName() + "' created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Unable to create role!");
        }
    }
}
