package com.smartrecipe.service;

import com.smartrecipe.entity.FavoriteRecipe;
import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.User;
import com.smartrecipe.repository.FavoriteRecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoriteRecipeService {

    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public FavoriteRecipeService(FavoriteRecipeRepository favoriteRecipeRepository) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
    }

    public void addToFavorites(User user, Recipe recipe) {

        boolean exists = favoriteRecipeRepository
                .existsByUserIdAndRecipeId(user.getId(), recipe.getId());

        if (exists) {
            throw new IllegalStateException("Recipe already in favorites");
        }

        FavoriteRecipe favorite = FavoriteRecipe.builder()
                .user(user)
                .recipe(recipe)
                .addedAt(LocalDateTime.now())
                .build();
        favoriteRecipeRepository.save(favorite);
    }

    public List<FavoriteRecipe> getUserFavorites(Long userId) {
        return favoriteRecipeRepository.findByUserId(userId);
    }
}
