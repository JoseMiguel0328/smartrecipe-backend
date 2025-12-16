package com.smartrecipe.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class RecipeIngredientUpdateRequest {

    @Positive(message = "Quantity must be greater than 0")
    private Double quantity;

    @Size(min = 1, max = 20, message = "Unit must be between 1 and 20 characters")
    private String unit;

    private Integer order;

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