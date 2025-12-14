package com.smartrecipe.dto.response;

public class RecipeResponse {

    private Long id;
    private String title;
    private String description;
    private boolean isPublic;

    public RecipeResponse(Long id, String title, String description, boolean isPublic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
