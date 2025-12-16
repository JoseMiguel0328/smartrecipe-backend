package com.smartrecipe.controller;

import com.smartrecipe.dto.request.AllergyCreateRequest;
import com.smartrecipe.dto.request.AllergyUpdateRequest;
import com.smartrecipe.dto.response.AllergyResponse;
import com.smartrecipe.entity.Allergy;
import com.smartrecipe.service.AllergyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/allergies")
public class AllergyController {

    private final AllergyService allergyService;

    public AllergyController(AllergyService allergyService) {
        this.allergyService = allergyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AllergyResponse createAllergy(@RequestBody @Valid AllergyCreateRequest request) {
        Allergy allergy = Allergy.builder().name(request.getName()).build();
        Allergy created = allergyService.createAllergy(allergy);
        return new AllergyResponse(created.getId(), created.getName());
    }

    @GetMapping
    public List<AllergyResponse> getAllAllergies() {
        return allergyService.findAll().stream()
                .map(allergy -> new AllergyResponse(allergy.getId(), allergy.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AllergyResponse getAllergyById(@PathVariable Long id) {
        Allergy allergy = allergyService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Allergy not found with id: " + id
                ));
        return new AllergyResponse(allergy.getId(), allergy.getName());
    }

    @GetMapping("/search")
    public ResponseEntity<AllergyResponse> searchByName(@RequestParam String name) {
        return allergyService.findByName(name)
                .map(allergy -> ResponseEntity.ok(
                        new AllergyResponse(allergy.getId(), allergy.getName())
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public AllergyResponse updateAllergy(
            @PathVariable Long id,
            @RequestBody @Valid AllergyUpdateRequest request
    ) {
        Allergy updated = allergyService.updateName(id, request.getName());
        return new AllergyResponse(updated.getId(), updated.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllergy(@PathVariable Long id) {
        allergyService.deleteAllergy(id);
    }
}