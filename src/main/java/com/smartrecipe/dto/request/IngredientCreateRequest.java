package com.smartrecipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class IngredientCreateRequest {

    @NotBlank(message = "Ingredient name cannot be blank")
    @Size(min = 2, max = 100, message = "Ingredient name must be between 2 and 100 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}