package com.smartrecipe.dto.response;

public class CuisineResponse {

    private Long id;
    private String name;

    public CuisineResponse(Long id, String name) {
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