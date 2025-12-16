package com.smartrecipe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TagCreateRequest {

    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 2, max = 50, message = "Tag name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Tag must contain only lowercase letters, numbers, and hyphens")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}