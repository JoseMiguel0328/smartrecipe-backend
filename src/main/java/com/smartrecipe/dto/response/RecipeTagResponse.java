package com.smartrecipe.dto.response;

import java.time.LocalDateTime;

public class RecipeTagResponse {

    private Long id;
    private Long tagId;
    private String tagName;
    private Long addedByUserId;
    private String addedByUsername;
    private LocalDateTime addedAt;

    public RecipeTagResponse(
            Long id,
            Long tagId,
            String tagName,
            Long addedByUserId,
            String addedByUsername,
            LocalDateTime addedAt
    ) {
        this.id = id;
        this.tagId = tagId;
        this.tagName = tagName;
        this.addedByUserId = addedByUserId;
        this.addedByUsername = addedByUsername;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public Long getAddedByUserId() {
        return addedByUserId;
    }

    public String getAddedByUsername() {
        return addedByUsername;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}