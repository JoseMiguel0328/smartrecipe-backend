package com.smartrecipe.repository;

import com.smartrecipe.entity.FavoriteRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {

    List<FavoriteRecipe> findByUserId(Long userId);

    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
}
