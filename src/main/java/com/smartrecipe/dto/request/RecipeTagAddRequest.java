package com.smartrecipe.dto.request;

import jakarta.validation.constraints.Size;

public class RecipeTagAddRequest {

    private Long tagId;

    @Size(min = 2, max = 50, message = "Tag name must be between 2 and 50 characters")
    private String tagName;

    private Long addedByUserId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(Long addedByUserId) {
        this.addedByUserId = addedByUserId;
    }
}