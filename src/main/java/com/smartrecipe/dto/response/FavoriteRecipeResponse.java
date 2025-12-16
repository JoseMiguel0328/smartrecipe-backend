package com.smartrecipe.dto.response;

import java.time.LocalDateTime;


public class FavoriteRecipeResponse {

    private Long id;
    private Long recipeId;
    private String recipeTitle;
    private LocalDateTime addedAt;

    public FavoriteRecipeResponse(
            Long id,
            Long recipeId,
            String recipeTitle,
            LocalDateTime addedAt
    ) {
        this.id = id;
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}