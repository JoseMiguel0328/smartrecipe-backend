package com.smartrecipe.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuesta de perfil completo de usuario.
 * Incluye estadísticas y alergias.
 */
public class UserProfileResponse {

    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    private long recipeCount;
    private long favoriteCount;

    private List<UserAllergyResponse> allergies;

    public UserProfileResponse(
            Long id,
            String username,
            String email,
            LocalDateTime createdAt,
            long recipeCount,
            long favoriteCount,
            List<UserAllergyResponse> allergies
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.recipeCount = recipeCount;
        this.favoriteCount = favoriteCount;
        this.allergies = allergies;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public long getRecipeCount() {
        return recipeCount;
    }

    public long getFavoriteCount() {
        return favoriteCount;
    }

    public List<UserAllergyResponse> getAllergies() {
        return allergies;
    }
}