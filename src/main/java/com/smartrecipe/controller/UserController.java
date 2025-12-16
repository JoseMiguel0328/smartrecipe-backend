package com.smartrecipe.controller;

import com.smartrecipe.dto.request.UserCreateRequest;
import com.smartrecipe.dto.request.UserUpdateRequest;
import com.smartrecipe.dto.response.UserProfileResponse;
import com.smartrecipe.dto.response.UserResponse;
import com.smartrecipe.dto.response.UserAllergyResponse;
import com.smartrecipe.entity.User;
import com.smartrecipe.service.FavoriteRecipeService;
import com.smartrecipe.service.RecipeService;
import com.smartrecipe.service.UserAllergyService;
import com.smartrecipe.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;
    private final FavoriteRecipeService favoriteRecipeService;
    private final UserAllergyService userAllergyService;

    public UserController(
            UserService userService,
            RecipeService recipeService,
            FavoriteRecipeService favoriteRecipeService,
            UserAllergyService userAllergyService
    ) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.favoriteRecipeService = favoriteRecipeService;
        this.userAllergyService = userAllergyService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserCreateRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // TODO: Hashear con BCrypt cuando implementemos JWT
                .build();

        User created = userService.createUser(user);

        return new UserResponse(
                created.getId(),
                created.getUsername(),
                created.getEmail(),
                created.getCreatedAt()
        );
    }


    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    @GetMapping("/{id}/profile")
    public UserProfileResponse getUserProfile(@PathVariable Long id) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        long recipeCount = recipeService.countRecipesByUser(id);
        long favoriteCount = favoriteRecipeService.getUserFavorites(id).size();

        List<UserAllergyResponse> allergies = userAllergyService.getUserAllergies(id)
                .stream()
                .map(ua -> new UserAllergyResponse(
                        ua.getId(),
                        ua.getAllergy().getId(),
                        ua.getAllergy().getName(),
                        ua.getAddedAt()
                ))
                .collect(Collectors.toList());

        return new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                recipeCount,
                favoriteCount,
                allergies
        );
    }


    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }


    @GetMapping("/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        if (request.getUsername() != null) {
            user = userService.updateUsername(id, request.getUsername());
        }

        if (request.getEmail() != null) {
            user = userService.updateEmail(id, request.getEmail());
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}