package com.smartrecipe.service;

import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.User;
import com.smartrecipe.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserService userService;

    public RecipeService(
            RecipeRepository recipeRepository,
            UserService userService
    ) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    public Recipe createRecipe(Recipe recipe, Long authorId) {
        User author = userService.findById(authorId).orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + authorId));

        if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe title cannot be empty");
        }

        if (recipe.getIsPublic() == null) {
            recipe.setIsPublic(false); // Default: privada
        }

        recipe.setAuthor(author);
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    public Recipe createRecipe(Recipe recipe, User author) {
        if (recipe.getTitle() == null || recipe.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Recipe title cannot be empty");
        }

        if (recipe.getIsPublic() == null) {
            recipe.setIsPublic(false); // Default: privada
        }

        recipe.setAuthor(author);
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setUpdatedAt(LocalDateTime.now());

        return recipeRepository.save(recipe);
    }

    @Transactional(readOnly = true)
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getPublicRecipes() {
        return recipeRepository.findByIsPublicTrue();
    }

    @Transactional(readOnly = true)
    public List<Recipe> getRecipesByUser(Long userId) {
        if (!userService.findById(userId).isPresent()) {
            throw new IllegalArgumentException(
                    "User not found with id: " + userId
            );
        }

        return recipeRepository.findByAuthorId(userId);
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe updateRecipe(Long recipeId, Recipe updatedRecipe) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        if (updatedRecipe.getTitle() != null && !updatedRecipe.getTitle().trim().isEmpty()) {
            recipe.setTitle(updatedRecipe.getTitle().trim());
        }

        if (updatedRecipe.getDescription() != null) {
            recipe.setDescription(updatedRecipe.getDescription());
        }

        if (updatedRecipe.getPreparationTime() != null) {
            if (updatedRecipe.getPreparationTime() < 0) {
                throw new IllegalArgumentException("Preparation time cannot be negative");
            }
            recipe.setPreparationTime(updatedRecipe.getPreparationTime());
        }

        if (updatedRecipe.getIsPublic() != null) {
            recipe.setIsPublic(updatedRecipe.getIsPublic());
        }

        recipe.setUpdatedAt(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    public Recipe updateTitle(Long recipeId, String newTitle) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        if (newTitle == null || newTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        recipe.setTitle(newTitle.trim());
        recipe.setUpdatedAt(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    public Recipe updateVisibility(Long recipeId, Boolean isPublic) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        recipe.setIsPublic(isPublic);
        recipe.setUpdatedAt(LocalDateTime.now());
        return recipeRepository.save(recipe);
    }

    @Transactional(readOnly = true)
    public boolean isAuthor(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (recipe == null) {
            return false;
        }
        return recipe.getAuthor().getId().equals(userId);
    }

    public void deleteRecipe(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new IllegalArgumentException("Recipe not found with id: " + recipeId);
        }
        recipeRepository.deleteById(recipeId);
    }

    @Transactional(readOnly = true)
    public long countPublicRecipes() {
        return recipeRepository.findByIsPublicTrue().size();
    }

    @Transactional(readOnly = true)
    public long countRecipesByUser(Long userId) {
        return recipeRepository.findByAuthorId(userId).size();
    }
}
