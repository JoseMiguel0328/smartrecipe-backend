package com.smartrecipe.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class RecipeUpdateRequest {

    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @Min(value = 0, message = "Preparation time cannot be negative")
    private Integer preparationTime;

    private Boolean isPublic;

    private Long cuisineId;

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

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Long cuisineId) {
        this.cuisineId = cuisineId;
    }
}