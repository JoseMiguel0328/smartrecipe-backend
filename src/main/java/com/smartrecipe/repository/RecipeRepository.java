package com.smartrecipe.repository;

import com.smartrecipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByIsPublicTrue();

    List<Recipe> findByAuthorId(Long authorId);
}
