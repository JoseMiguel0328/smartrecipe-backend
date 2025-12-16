package com.smartrecipe.service;

import com.smartrecipe.entity.Allergy;
import com.smartrecipe.entity.User;
import com.smartrecipe.entity.UserAllergy;
import com.smartrecipe.repository.UserAllergyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserAllergyService {

    private final UserAllergyRepository userAllergyRepository;
    private final UserService userService;
    private final AllergyService allergyService;

    public UserAllergyService(
            UserAllergyRepository userAllergyRepository,
            UserService userService,
            AllergyService allergyService
    ) {
        this.userAllergyRepository = userAllergyRepository;
        this.userService = userService;
        this.allergyService = allergyService;
    }

    public UserAllergy addAllergyToUser(Long userId, Long allergyId) {
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Allergy allergy = allergyService.findById(allergyId).orElseThrow(() -> new IllegalArgumentException("Allergy not found with id: " + allergyId));

        if (userAllergyRepository.existsByUserIdAndAllergyId(userId, allergyId)) {
            throw new IllegalStateException("User already has this allergy registered");
        }

        UserAllergy userAllergy = UserAllergy.builder()
                .user(user)
                .allergy(allergy)
                .addedAt(LocalDateTime.now())
                .build();

        return userAllergyRepository.save(userAllergy);
    }

    public UserAllergy addAllergyToUserByName(Long userId, String allergyName) {
        User user = userService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Allergy allergy = allergyService.findOrCreateByName(allergyName);

        if (userAllergyRepository.existsByUserIdAndAllergyId(userId, allergy.getId())) {
            throw new IllegalStateException("User already has this allergy registered");
        }

        UserAllergy userAllergy = UserAllergy.builder()
                .user(user)
                .allergy(allergy)
                .addedAt(LocalDateTime.now())
                .build();

        return userAllergyRepository.save(userAllergy);
    }

    @Transactional(readOnly = true)
    public List<UserAllergy> getUserAllergies(Long userId) {
        return userAllergyRepository.findByUserId(userId);
    }

    public void removeAllergyFromUser(Long userAllergyId) {
        if (!userAllergyRepository.existsById(userAllergyId)) {
            throw new IllegalArgumentException("UserAllergy not found with id: " + userAllergyId);
        }
        userAllergyRepository.deleteById(userAllergyId);
    }

    public void removeAllAllergiesFromUser(Long userId) {
        List<UserAllergy> allergies = userAllergyRepository.findByUserId(userId);
        userAllergyRepository.deleteAll(allergies);
    }
}