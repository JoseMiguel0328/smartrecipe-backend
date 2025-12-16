package com.smartrecipe.service;

import com.smartrecipe.entity.Cuisine;
import com.smartrecipe.repository.CuisineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    public Cuisine createCuisine(Cuisine cuisine) {
        String normalizedName = cuisine.getName().trim();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Cuisine name cannot be empty");
        }

        cuisine.setName(normalizedName);
        return cuisineRepository.save(cuisine);
    }

    @Transactional(readOnly = true)
    public Optional<Cuisine> findById(Long id) {
        return cuisineRepository.findById(id);
    }


    @Transactional(readOnly = true)
    public List<Cuisine> findAll() {
        return cuisineRepository.findAll();
    }

    public Cuisine updateName(Long id, String newName) {
        Cuisine cuisine = cuisineRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cuisine not found with id: " + id));

        String normalizedName = newName.trim();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Cuisine name cannot be empty");
        }

        cuisine.setName(normalizedName);
        return cuisineRepository.save(cuisine);
    }

    public void deleteCuisine(Long id) {
        if (!cuisineRepository.existsById(id)) {
            throw new IllegalArgumentException("Cuisine not found with id: " + id);
        }

        // TODO: Validar que no esté siendo usada en recetas
        // if (recipeRepository.existsByCuisineId(id)) {
        //     throw new IllegalStateException("Cannot delete cuisine in use");
        // }

        cuisineRepository.deleteById(id);
    }
}