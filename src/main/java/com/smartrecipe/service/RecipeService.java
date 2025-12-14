package com.smartrecipe.service;

import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.User;
import com.smartrecipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    public Recipe createRecipe(Recipe recipe, User author){
        recipe.setAuthor(author);
        return recipeRepository.save(recipe);
    }

    public List<Recipe> getPublicRecipes(){
        return recipeRepository.findByIsPublicTrue();
    }

    public List<Recipe> getRecipesByUser(Long userId){
        return recipeRepository.findByAuthorId(userId);
    }

}
