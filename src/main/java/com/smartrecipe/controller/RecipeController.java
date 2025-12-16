package com.smartrecipe.controller;

import com.smartrecipe.dto.request.RecipeCreateRequest;
import com.smartrecipe.dto.request.RecipeUpdateRequest;
import com.smartrecipe.dto.response.RecipeDetailResponse;
import com.smartrecipe.dto.response.RecipeIngredientResponse;
import com.smartrecipe.dto.response.RecipeResponse;
import com.smartrecipe.dto.response.RecipeTagResponse;
import com.smartrecipe.entity.Recipe;
import com.smartrecipe.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<RecipeResponse> createRecipe(
            @PathVariable Long userId,
            @RequestBody @Valid RecipeCreateRequest request) {

        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setPreparationTime(request.getPreparationTime());
        recipe.setIsPublic(request.getIsPublic() != null ? request.getIsPublic() : false);

        Recipe saved = recipeService.createRecipe(recipe, userId, request.getCuisineId());

        return ResponseEntity.status(HttpStatus.CREATED).body(toRecipeResponse(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailResponse> getRecipeById(@PathVariable Long id) {
        Recipe recipe = recipeService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + id));

        return ResponseEntity.ok(toRecipeDetailResponse(recipe));
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<RecipeResponse> responses = recipes.stream()
                .map(this::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/public")
    public ResponseEntity<List<RecipeResponse>> getPublicRecipes() {
        List<Recipe> recipes = recipeService.getPublicRecipes();
        List<RecipeResponse> responses = recipes.stream()
                .map(this::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecipeResponse>> getRecipesByUser(@PathVariable Long userId) {
        List<Recipe> recipes = recipeService.getRecipesByUser(userId);
        List<RecipeResponse> responses = recipes.stream()
                .map(this::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/cuisine/{cuisineId}")
    public ResponseEntity<List<RecipeResponse>> getRecipesByCuisine(@PathVariable Long cuisineId) {
        List<Recipe> recipes = recipeService.getRecipesByCuisine(cuisineId);
        List<RecipeResponse> responses = recipes.stream()
                .map(this::toRecipeResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            @PathVariable Long id,
            @RequestBody @Valid RecipeUpdateRequest request) {

        Recipe updatedData = new Recipe();
        updatedData.setTitle(request.getTitle());
        updatedData.setDescription(request.getDescription());
        updatedData.setPreparationTime(request.getPreparationTime());
        updatedData.setIsPublic(request.getIsPublic());

        Recipe updated = recipeService.updateRecipe(id, updatedData, request.getCuisineId());

        return ResponseEntity.ok(toRecipeResponse(updated));
    }

    @PatchMapping("/{id}/visibility")
    public ResponseEntity<RecipeResponse> updateVisibility(
            @PathVariable Long id,
            @RequestParam Boolean isPublic) {

        Recipe updated = recipeService.updateVisibility(id, isPublic);
        return ResponseEntity.ok(toRecipeResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    private RecipeResponse toRecipeResponse(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getIsPublic(),
                recipe.getAuthor().getId(),
                recipe.getAuthor().getUsername(),
                recipe.getCuisine() != null ? recipe.getCuisine().getId() : null,
                recipe.getCuisine() != null ? recipe.getCuisine().getName() : null,
                recipe.getCreatedAt(),
                recipe.getUpdatedAt()
        );
    }

    private RecipeDetailResponse toRecipeDetailResponse(Recipe recipe) {
        List<RecipeIngredientResponse> ingredients = recipe.getIngredients() != null
                ? recipe.getIngredients().stream()
                .map(ri -> new RecipeIngredientResponse(
                        ri.getId(),
                        ri.getIngredient().getId(),
                        ri.getIngredient().getName(),
                        ri.getQuantity(),
                        ri.getUnit(),
                        ri.getOrder()))
                .collect(Collectors.toList())
                : List.of();

        List<RecipeTagResponse> tags = recipe.getTags() != null
                ? recipe.getTags().stream()
                .map(rt -> new RecipeTagResponse(
                        rt.getId(),
                        rt.getTag().getId(),
                        rt.getTag().getName(),
                        rt.getAddedBy().getId(),
                        rt.getAddedBy().getUsername(),
                        rt.getAddedAt()))
                .collect(Collectors.toList())
                : List.of();

        return new RecipeDetailResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getPreparationTime(),
                recipe.getIsPublic(),
                recipe.getAuthor().getId(),
                recipe.getAuthor().getUsername(),
                recipe.getCuisine() != null ? recipe.getCuisine().getId() : null,
                recipe.getCuisine() != null ? recipe.getCuisine().getName() : null,
                recipe.getCreatedAt(),
                recipe.getUpdatedAt(),
                ingredients,
                tags
        );
    }
}
