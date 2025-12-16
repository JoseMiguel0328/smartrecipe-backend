package com.smartrecipe.controller;

import com.smartrecipe.dto.request.IngredientCreateRequest;
import com.smartrecipe.dto.request.IngredientUpdateRequest;
import com.smartrecipe.dto.response.IngredientResponse;
import com.smartrecipe.entity.Ingredient;
import com.smartrecipe.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse createIngredient(
            @RequestBody @Valid IngredientCreateRequest request
    ) {
        Ingredient ingredient = Ingredient.builder()
                .name(request.getName())
                .build();

        Ingredient created = ingredientService.createIngredient(ingredient);

        return new IngredientResponse(created.getId(), created.getName());
    }

    @GetMapping
    public List<IngredientResponse> getAllIngredients() {
        return ingredientService.findAll().stream()
                .map(ingredient -> new IngredientResponse(
                        ingredient.getId(),
                        ingredient.getName()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public IngredientResponse getIngredientById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.findById(id).orElseThrow(() -> new IllegalArgumentException("Ingredient not found with id: " + id));

        return new IngredientResponse(ingredient.getId(), ingredient.getName());
    }

    @GetMapping("/search")
    public ResponseEntity<IngredientResponse> searchByName(@RequestParam String name) {
        return ingredientService.findByName(name)
                .map(ingredient -> ResponseEntity.ok(
                        new IngredientResponse(ingredient.getId(), ingredient.getName())
                ))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    public IngredientResponse updateIngredient(
            @PathVariable Long id,
            @RequestBody @Valid IngredientUpdateRequest request
    ) {
        Ingredient updated = ingredientService.updateName(id, request.getName());
        return new IngredientResponse(updated.getId(), updated.getName());
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }
}