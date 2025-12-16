package com.smartrecipe.dto.request;

import jakarta.validation.constraints.*;


public class RecipeIngredientAddRequest {

    // Opción 1: Usar un ingrediente existente
    private Long ingredientId;

    // Opción 2: Crear un ingrediente nuevo (si no existe)
    @Size(min = 2, max = 100, message = "Ingredient name must be between 2 and 100 characters")
    private String ingredientName;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than 0")
    private Double quantity;

    @NotBlank(message = "Unit is required")
    @Size(min = 1, max = 20, message = "Unit must be between 1 and 20 characters")
    private String unit;

    private Integer order;

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}