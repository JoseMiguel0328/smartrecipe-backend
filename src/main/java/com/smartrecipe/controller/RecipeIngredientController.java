package com.smartrecipe.controller;

import com.smartrecipe.dto.request.RecipeIngredientAddRequest;
import com.smartrecipe.dto.request.RecipeIngredientUpdateRequest;
import com.smartrecipe.dto.response.RecipeIngredientResponse;
import com.smartrecipe.entity.RecipeIngredient;
import com.smartrecipe.service.RecipeIngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes/{recipeId}/ingredients")
public class RecipeIngredientController {

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientController(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeIngredientResponse addIngredientToRecipe(
            @PathVariable Long recipeId,
            @RequestBody @Valid RecipeIngredientAddRequest request
    ) {
        if (request.getIngredientId() != null && request.getIngredientName() != null) {
            throw new IllegalArgumentException(
                    "Send either ingredientId or ingredientName, not both"
            );
        }

        if (request.getIngredientId() == null && request.getIngredientName() == null) {
            throw new IllegalArgumentException(
                    "Either ingredientId or ingredientName is required"
            );
        }

        RecipeIngredient recipeIngredient;

        if (request.getIngredientId() != null) {
            recipeIngredient = recipeIngredientService.addIngredientToRecipe(
                    recipeId,
                    request.getIngredientId(),
                    request.getQuantity(),
                    request.getUnit()
            );
        } else {
            recipeIngredient = recipeIngredientService.addIngredientToRecipeByName(
                    recipeId,
                    request.getIngredientName(),
                    request.getQuantity(),
                    request.getUnit()
            );
        }

        if (request.getOrder() != null) {
            recipeIngredient = recipeIngredientService.updateOrder(
                    recipeIngredient.getId(),
                    request.getOrder()
            );
        }

        return new RecipeIngredientResponse(
                recipeIngredient.getId(),
                recipeIngredient.getIngredient().getId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity(),
                recipeIngredient.getUnit(),
                recipeIngredient.getOrder()
        );
    }


    @GetMapping
    public List<RecipeIngredientResponse> getRecipeIngredients(@PathVariable Long recipeId) {
        return recipeIngredientService.getRecipeIngredients(recipeId).stream()
                .map(ri -> new RecipeIngredientResponse(
                        ri.getId(),
                        ri.getIngredient().getId(),
                        ri.getIngredient().getName(),
                        ri.getQuantity(),
                        ri.getUnit(),
                        ri.getOrder()
                ))
                .collect(Collectors.toList());
    }


    @PatchMapping("/{ingredientId}")
    public RecipeIngredientResponse updateIngredient(
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId,
            @RequestBody @Valid RecipeIngredientUpdateRequest request
    ) {
        RecipeIngredient recipeIngredient = recipeIngredientService
                .getRecipeIngredients(recipeId).stream()
                .filter(ri -> ri.getId().equals(ingredientId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ingredient not found in this recipe"
                ));

        if (request.getQuantity() != null) {
            recipeIngredient = recipeIngredientService.updateQuantity(
                    ingredientId,
                    request.getQuantity()
            );
        }

        if (request.getUnit() != null) {
            recipeIngredient = recipeIngredientService.updateUnit(
                    ingredientId,
                    request.getUnit()
            );
        }

        if (request.getOrder() != null) {
            recipeIngredient = recipeIngredientService.updateOrder(
                    ingredientId,
                    request.getOrder()
            );
        }

        return new RecipeIngredientResponse(
                recipeIngredient.getId(),
                recipeIngredient.getIngredient().getId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity(),
                recipeIngredient.getUnit(),
                recipeIngredient.getOrder()
        );
    }

    @DeleteMapping("/{ingredientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeIngredientFromRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long ingredientId
    ) {
        recipeIngredientService.removeIngredientFromRecipe(ingredientId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllIngredientsFromRecipe(@PathVariable Long recipeId) {
        recipeIngredientService.removeAllIngredientsFromRecipe(recipeId);
    }
}