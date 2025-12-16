package com.smartrecipe.dto.response;

import java.time.LocalDateTime;

public class UserAllergyResponse {

    private Long id;
    private Long allergyId;
    private String allergyName;
    private LocalDateTime addedAt;

    public UserAllergyResponse(
            Long id,
            Long allergyId,
            String allergyName,
            LocalDateTime addedAt
    ) {
        this.id = id;
        this.allergyId = allergyId;
        this.allergyName = allergyName;
        this.addedAt = addedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getAllergyId() {
        return allergyId;
    }

    public String getAllergyName() {
        return allergyName;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }
}