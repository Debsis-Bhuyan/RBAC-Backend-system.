package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.model.User;
import com.debasis.SecureRBAC.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') or #username == authentication.name")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);

//                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        if (Objects.isNull(user)){
            throw new RuntimeException("User not found with username: " + username);
        }
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        if (Objects.isNull(user)){
            throw new RuntimeException("User not found with username: " + username);
        }
        userRepository.delete(user);

        return ResponseEntity.ok("User deleted successfully!");
    }
}

