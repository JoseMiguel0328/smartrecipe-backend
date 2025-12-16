package com.smartrecipe.dto.request;

import jakarta.validation.constraints.Size;

public class UserAllergyAddRequest {

    private Long allergyId;

    @Size(min = 2, max = 100, message = "Allergy name must be between 2 and 100 characters")
    private String allergyName;

    public Long getAllergyId() {
        return allergyId;
    }

    public void setAllergyId(Long allergyId) {
        this.allergyId = allergyId;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }
}
