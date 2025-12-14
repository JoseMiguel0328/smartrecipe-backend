package com.smartrecipe.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RecipeCreateRequest {
    @NotBlank
    private String title;

    private String description;

    private boolean isPublic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
