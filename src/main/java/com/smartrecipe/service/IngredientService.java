package com.smartrecipe.service;

import com.smartrecipe.entity.Ingredient;
import com.smartrecipe.repository.IngredientRepository;
import com.smartrecipe.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public Ingredient findOrCreateByName(String name) {
        return ingredientRepository.findByNameIgnoreCase(name).orElseGet(() -> {
                    Ingredient newIngredient = Ingredient.builder()
                            .name(name.trim())
                            .build();
                    return ingredientRepository.save(newIngredient);
                });
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        String normalizedName = ingredient.getName().trim();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be empty");
        }

        if (ingredientRepository.findByNameIgnoreCase(normalizedName).isPresent()) {
            throw new IllegalArgumentException(
                    "Ingredient already exists: " + normalizedName
            );
        }

        ingredient.setName(normalizedName);
        return ingredientRepository.save(ingredient);
    }

    @Transactional(readOnly = true)
    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Ingredient> findByName(String name) {
        return ingredientRepository.findByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient updateName(Long id, String newName) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ingredient not found with id: " + id));

        String normalizedName = newName.trim();

        Optional<Ingredient> existing = ingredientRepository.findByNameIgnoreCase(normalizedName);
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Ingredient name already exists: " + normalizedName);
        }

        ingredient.setName(normalizedName);
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new IllegalArgumentException("Ingredient not found with id: " + id);
        }

        if (recipeIngredientRepository.existsByIngredientId(id)){
            throw new IllegalStateException("Cannot delete ingredient: it is being used in recipes");
        }

        ingredientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return ingredientRepository.findByNameIgnoreCase(name).isPresent();
    }
}
