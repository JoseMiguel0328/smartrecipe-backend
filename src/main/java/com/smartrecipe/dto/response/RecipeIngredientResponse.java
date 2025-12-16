package com.smartrecipe.dto.response;

public class RecipeIngredientResponse {

    private Long id;
    private Long ingredientId;
    private String ingredientName;
    private Double quantity;
    private String unit;
    private Integer order;

    public RecipeIngredientResponse(
            Long id,
            Long ingredientId,
            String ingredientName,
            Double quantity,
            String unit,
            Integer order
    ) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getOrder() {
        return order;
    }
}