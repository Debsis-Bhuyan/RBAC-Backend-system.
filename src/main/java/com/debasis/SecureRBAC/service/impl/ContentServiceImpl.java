package com.debasis.SecureRBAC.service.impl;

import com.debasis.SecureRBAC.model.Content;
import com.debasis.SecureRBAC.repository.ContentRepository;
import com.debasis.SecureRBAC.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Content createContent(Content content, String createdBy) {
        content.setCreatedAt(LocalDateTime.now());
        content.setCreatedBy(createdBy);
        content.setStatus("Pending");
        return contentRepository.save(content);
    }

    @Override
    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    @Override
    public Content getContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + id));
    }

    @Override
    public Content updateContent(Long id, Content updatedContent, String modifiedBy) {
        Content existingContent = getContentById(id);
        existingContent.setTitle(updatedContent.getTitle());
        existingContent.setDescription(updatedContent.getDescription());
        existingContent.setStatus(updatedContent.getStatus());
        return contentRepository.save(existingContent);
    }

    @Override
    public void deleteContent(Long id) {
        Content existingContent = getContentById(id);
        contentRepository.delete(existingContent);
    }

    @Override
    public Content approveContent(Long id, String modifiedBy) {
        Content content = getContentById(id);
        content.setStatus("Approved");
        return contentRepository.save(content);
    }

    @Override
    public Content rejectContent(Long id, String reason, String modifiedBy) {
        Content content = getContentById(id);
        content.setStatus("Rejected");
        content.setDescription(content.getDescription() + " (Reason: " + reason + ")");
        return contentRepository.save(content);
    }

    @Override
    public List<Content> getUserSpecificContent(String username) {
        return contentRepository.findAll();
    }
}
