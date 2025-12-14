package com.smartrecipe.repository;

import com.smartrecipe.entity.RecipeTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeTagRepository extends JpaRepository<RecipeTag, Long> {
}
