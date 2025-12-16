package com.smartrecipe.controller;

import com.smartrecipe.dto.request.CuisineCreateRequest;
import com.smartrecipe.dto.request.CuisineUpdateRequest;
import com.smartrecipe.dto.response.CuisineResponse;
import com.smartrecipe.entity.Cuisine;
import com.smartrecipe.service.CuisineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cuisines")
public class CuisineController {

    private final CuisineService cuisineService;

    public CuisineController(CuisineService cuisineService) {
        this.cuisineService = cuisineService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuisineResponse createCuisine(@RequestBody @Valid CuisineCreateRequest request) {
        Cuisine cuisine = Cuisine.builder().name(request.getName()).build();
        Cuisine created = cuisineService.createCuisine(cuisine);
        return new CuisineResponse(created.getId(), created.getName());
    }

    @GetMapping
    public List<CuisineResponse> getAllCuisines() {
        return cuisineService.findAll().stream()
                .map(cuisine -> new CuisineResponse(cuisine.getId(), cuisine.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CuisineResponse getCuisineById(@PathVariable Long id) {
        Cuisine cuisine = cuisineService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Cuisine not found with id: " + id
                ));
        return new CuisineResponse(cuisine.getId(), cuisine.getName());
    }

    @GetMapping("/search")
    public ResponseEntity<CuisineResponse> searchByName(@RequestParam String name) {
        return cuisineService.findByName(name)
                .map(cuisine -> ResponseEntity.ok(
                        new CuisineResponse(cuisine.getId(), cuisine.getName())
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public CuisineResponse updateCuisine(
            @PathVariable Long id,
            @RequestBody @Valid CuisineUpdateRequest request
    ) {
        Cuisine updated = cuisineService.updateName(id, request.getName());
        return new CuisineResponse(updated.getId(), updated.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCuisine(@PathVariable Long id) {
        cuisineService.deleteCuisine(id);
    }
}