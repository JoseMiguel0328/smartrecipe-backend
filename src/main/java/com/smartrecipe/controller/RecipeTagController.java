package com.smartrecipe.controller;

import com.smartrecipe.dto.request.RecipeTagAddRequest;
import com.smartrecipe.dto.response.RecipeTagResponse;
import com.smartrecipe.entity.RecipeTag;
import com.smartrecipe.service.RecipeTagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes/{recipeId}/tags")
public class RecipeTagController {

    private final RecipeTagService recipeTagService;

    public RecipeTagController(RecipeTagService recipeTagService) {
        this.recipeTagService = recipeTagService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeTagResponse addTagToRecipe(
            @PathVariable Long recipeId,
            @RequestBody @Valid RecipeTagAddRequest request
    ) {
        if (request.getTagId() != null && request.getTagName() != null) {
            throw new IllegalArgumentException(
                    "Send either tagId or tagName, not both"
            );
        }

        if (request.getTagId() == null && request.getTagName() == null) {
            throw new IllegalArgumentException(
                    "Either tagId or tagName is required"
            );
        }

        RecipeTag recipeTag;

        if (request.getTagId() != null) {
            recipeTag = recipeTagService.addTagToRecipe(
                    recipeId,
                    request.getTagId(),
                    request.getAddedByUserId()
            );
        } else {
            recipeTag = recipeTagService.addTagToRecipeByName(
                    recipeId,
                    request.getTagId(),
                    request.getTagName(),
                    request.getAddedByUserId()
            );
        }

        return new RecipeTagResponse(
                recipeTag.getId(),
                recipeTag.getTag().getId(),
                recipeTag.getTag().getName(),
                recipeTag.getAddedBy() != null ? recipeTag.getAddedBy().getId() : null,
                recipeTag.getAddedBy() != null ? recipeTag.getAddedBy().getUsername() : null,
                recipeTag.getAddedAt()
        );
    }

    @GetMapping
    public List<RecipeTagResponse> getRecipeTags(@PathVariable Long recipeId) {
        return recipeTagService.getRecipeTags(recipeId).stream()
                .map(rt -> new RecipeTagResponse(
                        rt.getId(),
                        rt.getTag().getId(),
                        rt.getTag().getName(),
                        rt.getAddedBy() != null ? rt.getAddedBy().getId() : null,
                        rt.getAddedBy() != null ? rt.getAddedBy().getUsername() : null,
                        rt.getAddedAt()
                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{tagAssociationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTagFromRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long tagAssociationId
    ) {
        recipeTagService.removeTagFromRecipe(tagAssociationId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllTagsFromRecipe(@PathVariable Long recipeId) {
        recipeTagService.removeAllTagsFromRecipe(recipeId);
    }
}