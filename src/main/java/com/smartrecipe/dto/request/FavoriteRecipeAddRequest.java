package com.smartrecipe.dto.request;

import jakarta.validation.constraints.NotNull;

public class FavoriteRecipeAddRequest {

    @NotNull(message = "Recipe ID is required")
    private Long recipeId;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}