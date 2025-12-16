package com.smartrecipe.repository;

import com.smartrecipe.entity.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {

    List<RecipeTag> findByRecipeId(Long recipeId);

    boolean existsByRecipeIdAndTagId(Long recipeId, Long tagId);

    boolean existsByTagId(Long tagId);

}
