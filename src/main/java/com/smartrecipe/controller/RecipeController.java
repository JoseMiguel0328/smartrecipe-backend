package com.smartrecipe.controller;


import com.smartrecipe.dto.request.RecipeCreateRequest;
import com.smartrecipe.dto.response.RecipeResponse;
import com.smartrecipe.entity.Recipe;
import com.smartrecipe.entity.User;
import com.smartrecipe.service.RecipeService;
import com.smartrecipe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService){
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/user/{userId}")
    public RecipeResponse createRecipe(@PathVariable Long userId, @RequestBody @Valid RecipeCreateRequest request){

        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setIsPublic(request.isPublic());

        Recipe saved = recipeService.createRecipe(recipe, user);

        return new RecipeResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getDescription(),
                saved.getIsPublic()
        );
    }
}
