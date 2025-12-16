package com.smartrecipe.dto.response;

public class AllergyResponse {

    private Long id;
    private String name;

    public AllergyResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}