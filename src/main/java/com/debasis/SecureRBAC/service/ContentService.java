package com.debasis.SecureRBAC.service;

import com.debasis.SecureRBAC.model.Content;

import java.util.List;

public interface ContentService {

    Content createContent(Content content, String createdBy);

    List<Content> getAllContent();

    Content getContentById(Long id);

    Content updateContent(Long id, Content updatedContent, String modifiedBy);

    void deleteContent(Long id);

    Content approveContent(Long id, String modifiedBy);

    Content rejectContent(Long id, String reason, String modifiedBy);

    List<Content> getUserSpecificContent(String username);
}
