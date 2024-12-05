package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.exception.UserNotFoundException;
import com.debasis.SecureRBAC.model.Content;
import com.debasis.SecureRBAC.model.User;
import com.debasis.SecureRBAC.repository.UserRepository;
import com.debasis.SecureRBAC.service.ContentService;
import com.debasis.SecureRBAC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (Objects.isNull(user)){
            throw new UserNotFoundException("User not found with username: " + authentication.getName());
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/me/change-password")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.changePassword(authentication.getName(), oldPassword, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

//    @PostMapping("/content/create")
//    @PreAuthorize("hasRole('USER')")
//    public ResponseEntity<Content> createContent(@RequestBody Content content) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Content createdContent = contentService.createContent(content,  authentication.getName());
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
//    }
}

