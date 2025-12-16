package com.smartrecipe.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeDetailResponse {

    private Long id;
    private String title;
    private String description;
    private Integer preparationTime;
    private boolean isPublic;

    private Long authorId;
    private String authorUsername;

    private Long cuisineId;
    private String cuisineName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Listas relacionadas
    private List<RecipeIngredientResponse> ingredients;
    private List<RecipeTagResponse> tags;

    // Constructor
    public RecipeDetailResponse(
            Long id,
            String title,
            String description,
            Integer preparationTime,
            boolean isPublic,
            Long authorId,
            String authorUsername,
            Long cuisineId,
            String cuisineName,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            List<RecipeIngredientResponse> ingredients,
            List<RecipeTagResponse> tags
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.preparationTime = preparationTime;
        this.isPublic = isPublic;
        this.authorId = authorId;
        this.authorUsername = authorUsername;
        this.cuisineId = cuisineId;
        this.cuisineName = cuisineName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ingredients = ingredients;
        this.tags = tags;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public Long getCuisineId() {
        return cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<RecipeIngredientResponse> getIngredients() {
        return ingredients;
    }

    public List<RecipeTagResponse> getTags() {
        return tags;
    }
}