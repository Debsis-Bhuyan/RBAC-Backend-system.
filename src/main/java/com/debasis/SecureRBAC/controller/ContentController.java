package com.debasis.SecureRBAC.controller;

import com.debasis.SecureRBAC.model.Content;
import com.debasis.SecureRBAC.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentController {

    @Autowired
    private ContentService contentService;
    @PostMapping("/users/content/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Content> createContent(@RequestBody Content content) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Content createdContent = contentService.createContent(content, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContent);
    }

    @GetMapping("/moderator/content")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<List<Content>> getAllContent() {
        return ResponseEntity.ok(contentService.getAllContent());
    }

    @GetMapping("/users/content/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Content> getContentById(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }

    @PutMapping("/users/content/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Content> updateContent(
            @PathVariable Long id,
            @RequestBody Content updatedContent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(contentService.updateContent(id, updatedContent, authentication.getName()));
    }

    @DeleteMapping("/admin/content/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteContent(@PathVariable Long id) {
        try {
            contentService.deleteContent(id);
            return ResponseEntity.ok("Content deleted successfully");
        }
        catch (Exception ex){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/moderator/content/approve")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Content> approveContent(@RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(contentService.approveContent(id, authentication.getName()));
    }

    @PostMapping("/moderator/content/reject")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<Content> rejectContent(
            @RequestParam Long id,
            @RequestParam String reason) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(contentService.rejectContent(id, reason, authentication.getName()));
    }

    @GetMapping("/users/content")
    public ResponseEntity<List<Content>> getUserSpecificContent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(contentService.getUserSpecificContent(authentication.getName()));
    }
}


