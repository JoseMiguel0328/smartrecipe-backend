package com.smartrecipe.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(name = "preparation_time")
    private Integer preparationTime;

    @Column(nullable = false)
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuisine_id")
    private Cuisine cuisine;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeTag> tags;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<FavoriteRecipe> favorites;
}
