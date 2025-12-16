package com.smartrecipe.controller;

import com.smartrecipe.dto.request.UserAllergyAddRequest;
import com.smartrecipe.dto.response.UserAllergyResponse;
import com.smartrecipe.entity.UserAllergy;
import com.smartrecipe.service.UserAllergyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/allergies")
public class UserAllergyController {

    private final UserAllergyService userAllergyService;

    public UserAllergyController(UserAllergyService userAllergyService) {
        this.userAllergyService = userAllergyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserAllergyResponse addAllergyToUser(
            @PathVariable Long userId,
            @RequestBody @Valid UserAllergyAddRequest request
    ) {
        UserAllergy userAllergy;

        if (request.getAllergyId() != null && request.getAllergyName() != null) {
            throw new IllegalArgumentException(
                    "Send either allergyId or allergyName, not both"
            );
        }

        if (request.getAllergyId() == null && request.getAllergyName() == null) {
            throw new IllegalArgumentException(
                    "Either allergyId or allergyName is required"
            );
        }

        if (request.getAllergyId() != null) {
            userAllergy = userAllergyService.addAllergyToUser(
                    userId,
                    request.getAllergyId()
            );
        } else {
            userAllergy = userAllergyService.addAllergyToUserByName(
                    userId,
                    request.getAllergyName()
            );
        }

        return new UserAllergyResponse(
                userAllergy.getId(),
                userAllergy.getAllergy().getId(),
                userAllergy.getAllergy().getName(),
                userAllergy.getAddedAt()
        );
    }

    @GetMapping
    public List<UserAllergyResponse> getUserAllergies(@PathVariable Long userId) {
        return userAllergyService.getUserAllergies(userId).stream()
                .map(ua -> new UserAllergyResponse(
                        ua.getId(),
                        ua.getAllergy().getId(),
                        ua.getAllergy().getName(),
                        ua.getAddedAt()
                ))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{allergyAssociationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllergyFromUser(
            @PathVariable Long userId,
            @PathVariable Long allergyAssociationId
    ) {
        userAllergyService.removeAllergyFromUser(allergyAssociationId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAllAllergiesFromUser(@PathVariable Long userId) {
        userAllergyService.removeAllAllergiesFromUser(userId);
    }
}