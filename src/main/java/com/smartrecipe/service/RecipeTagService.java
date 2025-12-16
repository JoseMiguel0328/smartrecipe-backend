package com.smartrecipe.service;

import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.RecipeTag;
import com.smartrecipe.entity.Tag;
import com.smartrecipe.entity.User;
import com.smartrecipe.repository.RecipeTagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RecipeTagService {

    private final RecipeTagRepository recipeTagRepository;
    private final RecipeService recipeService;
    private final TagService tagService;
    private final UserService userService;

    public RecipeTagService(
            RecipeTagRepository recipeTagRepository,
            RecipeService recipeService,
            TagService tagService,
            UserService userService
    ) {
        this.recipeTagRepository = recipeTagRepository;
        this.recipeService = recipeService;
        this.tagService = tagService;
        this.userService = userService;
    }

    public RecipeTag addTagToRecipe(Long recipeId, Long tagId, Long addedByUserId) {

        Recipe recipe = recipeService.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        Tag tag = tagService.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + tagId));

        User addedBy = null;
        if (addedByUserId != null) {
            addedBy = userService.findById(addedByUserId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + addedByUserId));
        }

        // TODO: Validar que el tag no esté ya asociado
        // Necesitarías un método en el repository como:
        // boolean existsByRecipeIdAndTagId(Long recipeId, Long tagId);

        RecipeTag recipeTag = RecipeTag.builder()
                .recipe(recipe)
                .tag(tag)
                .addedBy(addedBy)
                .addedAt(LocalDateTime.now())
                .build();

        return recipeTagRepository.save(recipeTag);
    }

    public RecipeTag addTagToRecipeByName(Long recipeId, String tagName, Long addedByUserId) {
        Recipe recipe = recipeService.findById(recipeId).orElseThrow(() -> new IllegalArgumentException("Recipe not found with id: " + recipeId));

        Tag tag = tagService.findOrCreateByName(tagName);

        User addedBy = null;
        if (addedByUserId != null) {
            addedBy = userService.findById(addedByUserId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + addedByUserId));
        }

        RecipeTag recipeTag = RecipeTag.builder()
                .recipe(recipe)
                .tag(tag)
                .addedBy(addedBy)
                .addedAt(LocalDateTime.now())
                .build();

        return recipeTagRepository.save(recipeTag);
    }


    @Transactional(readOnly = true)
    public List<RecipeTag> getRecipeTags(Long recipeId) {
        return recipeTagRepository.findAll().stream()
                .filter(rt -> rt.getRecipe().getId().equals(recipeId))
                .toList();
    }

    public void removeTagFromRecipe(Long recipeTagId) {
        if (!recipeTagRepository.existsById(recipeTagId)) {
            throw new IllegalArgumentException("RecipeTag not found with id: " + recipeTagId);
        }
        recipeTagRepository.deleteById(recipeTagId);
    }

    public void removeAllTagsFromRecipe(Long recipeId) {
        List<RecipeTag> tags = getRecipeTags(recipeId);
        recipeTagRepository.deleteAll(tags);
    }
}