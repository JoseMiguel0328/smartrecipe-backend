package com.smartrecipe.service;

import com.smartrecipe.entity.FavoriteRecipe;
import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.User;
import com.smartrecipe.repository.FavoriteRecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FavoriteRecipeService {

    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private final UserService userService;
    private final RecipeService recipeService;

    public FavoriteRecipeService(
            FavoriteRecipeRepository favoriteRecipeRepository,
            UserService userService,
            RecipeService recipeService
    ) {
        this.favoriteRecipeRepository = favoriteRecipeRepository;
        this.userService = userService;
        this.recipeService = recipeService;
    }

    public FavoriteRecipe addToFavorites(Long userId, Long recipeId) {

        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Recipe recipe = recipeService.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        boolean exists = favoriteRecipeRepository.existsByUserIdAndRecipeId(userId, recipeId);
        if(exists){
            throw new IllegalArgumentException("Recipe already in user's favorites");
        }

        FavoriteRecipe favorite = FavoriteRecipe.builder()
                .user(user)
                .recipe(recipe)
                .addedAt(LocalDateTime.now())
                .build();

        return favoriteRecipeRepository.save(favorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteRecipe> getUserFavorites(Long userId) {
        if (!userService.findById(userId).isPresent()) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }

        return favoriteRecipeRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<FavoriteRecipe> findById(Long favoriteId) {
        return favoriteRecipeRepository.findById(favoriteId);
    }

    @Transactional(readOnly = true)
    public boolean isFavorite(Long userId, Long recipeId) {
        return favoriteRecipeRepository.existsByUserIdAndRecipeId(userId, recipeId);
    }

    public void removeFromFavorites(Long favoriteId) {
        if (!favoriteRecipeRepository.existsById(favoriteId)) {
            throw new IllegalArgumentException(
                    "Favorite not found with id: " + favoriteId
            );
        }
        favoriteRecipeRepository.deleteById(favoriteId);
    }

    public void removeFromFavoritesByUserAndRecipe(Long userId, Long recipeId) {
        if (!favoriteRecipeRepository.existsByUserIdAndRecipeId(userId, recipeId)) {
            throw new IllegalStateException(
                    "Recipe is not in user's favorites"
            );
        }

        List<FavoriteRecipe> favorites = favoriteRecipeRepository.findByUserId(userId);
        favorites.stream()
                .filter(f -> f.getRecipe().getId().equals(recipeId))
                .findFirst()
                .ifPresent(favoriteRecipeRepository::delete);
    }

    public void removeAllFavoritesFromUser(Long userId) {
        List<FavoriteRecipe> favorites = favoriteRecipeRepository.findByUserId(userId);
        favoriteRecipeRepository.deleteAll(favorites);
    }

    @Transactional(readOnly = true)
    public long countFavoritesForRecipe(Long recipeId) {
        return favoriteRecipeRepository.findAll().stream()
                .filter(f -> f.getRecipe().getId().equals(recipeId))
                .count();
    }
}
