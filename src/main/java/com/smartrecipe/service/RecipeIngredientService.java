package com.smartrecipe.service;

import com.smartrecipe.entity.Ingredient;
import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.RecipeIngredient;
import com.smartrecipe.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeIngredientService(
            RecipeIngredientRepository recipeIngredientRepository,
            RecipeService recipeService,
            IngredientService ingredientService
    ) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }


    public RecipeIngredient addIngredientToRecipe(Long recipeId, Long ingredientId, Double quantity, String unit) {

        Recipe recipe = recipeService.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        Ingredient ingredient = ingredientService.findById(ingredientId).orElseThrow(() -> new IllegalArgumentException("Ingredient not found with id: " + ingredientId));

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }


        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be empty");
        }

        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .quantity(quantity)
                .unit(unit.trim())
                .build();

        return recipeIngredientRepository.save(recipeIngredient);
    }


    public RecipeIngredient addIngredientToRecipeByName(Long recipeId, String ingredientName, Double quantity, String unit) {

        Recipe recipe = recipeService.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        Ingredient ingredient = ingredientService.findOrCreateByName(ingredientName);

        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be empty");
        }

        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(recipe)
                .ingredient(ingredient)
                .quantity(quantity)
                .unit(unit.trim())
                .build();

        return recipeIngredientRepository.save(recipeIngredient);
    }

    @Transactional(readOnly = true)
    public List<RecipeIngredient> getRecipeIngredients(Long recipeId) {
        return recipeIngredientRepository.findByRecipeId(recipeId);
    }

    public RecipeIngredient updateQuantity(Long recipeIngredientId, Double newQuantity) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId).orElseThrow(() -> new IllegalArgumentException("RecipeIngredient not found with id: " + recipeIngredientId));

        if (newQuantity == null || newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        recipeIngredient.setQuantity(newQuantity);
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public RecipeIngredient updateUnit(Long recipeIngredientId, String newUnit) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId).orElseThrow(() -> new IllegalArgumentException("RecipeIngredient not found with id: " + recipeIngredientId));

        if (newUnit == null || newUnit.trim().isEmpty()) {
            throw new IllegalArgumentException("Unit cannot be empty");
        }

        recipeIngredient.setUnit(newUnit.trim());
        return recipeIngredientRepository.save(recipeIngredient);
    }


    public RecipeIngredient updateOrder(Long recipeIngredientId, Integer order) {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId).orElseThrow(() -> new IllegalArgumentException("RecipeIngredient not found with id: " + recipeIngredientId));

        recipeIngredient.setOrder(order);
        return recipeIngredientRepository.save(recipeIngredient);
    }

    public void removeIngredientFromRecipe(Long recipeIngredientId) {
        if (!recipeIngredientRepository.existsById(recipeIngredientId)) {
            throw new IllegalArgumentException("RecipeIngredient not found with id: " + recipeIngredientId);
        }
        recipeIngredientRepository.deleteById(recipeIngredientId);
    }

    public void removeAllIngredientsFromRecipe(Long recipeId) {
        List<RecipeIngredient> ingredients = recipeIngredientRepository.findByRecipeId(recipeId);
        recipeIngredientRepository.deleteAll(ingredients);
    }
}