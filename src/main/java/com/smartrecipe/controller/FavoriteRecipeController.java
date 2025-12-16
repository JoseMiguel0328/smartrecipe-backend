package com.smartrecipe.controller;

import com.smartrecipe.dto.request.FavoriteRecipeAddRequest;
import com.smartrecipe.dto.response.FavoriteRecipeResponse;
import com.smartrecipe.entity.FavoriteRecipe;
import com.smartrecipe.service.FavoriteRecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
public class FavoriteRecipeController {

    private final FavoriteRecipeService favoriteRecipeService;

    public FavoriteRecipeController(FavoriteRecipeService favoriteRecipeService) {
        this.favoriteRecipeService = favoriteRecipeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteRecipeResponse addToFavorites(
            @PathVariable Long userId,
            @RequestBody @Valid FavoriteRecipeAddRequest request
    ) {
        FavoriteRecipe favorite = favoriteRecipeService.addToFavorites(
                userId,
                request.getRecipeId()
        );

        return new FavoriteRecipeResponse(
                favorite.getId(),
                favorite.getRecipe().getId(),
                favorite.getRecipe().getTitle(),
                favorite.getAddedAt()
        );
    }


    @GetMapping
    public List<FavoriteRecipeResponse> getUserFavorites(@PathVariable Long userId) {
        return favoriteRecipeService.getUserFavorites(userId).stream()
                .map(fav -> new FavoriteRecipeResponse(
                        fav.getId(),
                        fav.getRecipe().getId(),
                        fav.getRecipe().getTitle(),
                        fav.getAddedAt()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{recipeId}/check")
    public IsFavoriteResponse checkIsFavorite(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        boolean isFavorite = favoriteRecipeService.isFavorite(userId, recipeId);
        return new IsFavoriteResponse(isFavorite);
    }


    @DeleteMapping("/{favoriteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromFavorites(
            @PathVariable Long userId,
            @PathVariable Long favoriteId
    ) {
        favoriteRecipeService.removeFromFavorites(favoriteId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllFavorites(@PathVariable Long userId) {
        favoriteRecipeService.removeAllFavoritesFromUser(userId);
    }

    public static class IsFavoriteResponse {
        private boolean isFavorite;

        public IsFavoriteResponse(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        public boolean isFavorite() {
            return isFavorite;
        }
    }
}