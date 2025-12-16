package com.smartrecipe.controller;

import com.smartrecipe.dto.request.TagCreateRequest;
import com.smartrecipe.dto.request.TagUpdateRequest;
import com.smartrecipe.dto.response.TagResponse;
import com.smartrecipe.entity.Tag;
import com.smartrecipe.service.TagService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagResponse createTag(@RequestBody @Valid TagCreateRequest request) {
        Tag tag = Tag.builder().name(request.getName()).build();
        Tag created = tagService.createTag(tag);
        return new TagResponse(created.getId(), created.getName());
    }

    @GetMapping
    public List<TagResponse> getAllTags() {
        return tagService.findAll().stream()
                .map(tag -> new TagResponse(tag.getId(), tag.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagResponse getTagById(@PathVariable Long id) {
        Tag tag = tagService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Tag not found with id: " + id
                ));
        return new TagResponse(tag.getId(), tag.getName());
    }

    @GetMapping("/search")
    public ResponseEntity<TagResponse> searchByName(@RequestParam String name) {
        return tagService.findByName(name)
                .map(tag -> ResponseEntity.ok(
                        new TagResponse(tag.getId(), tag.getName())
                ))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public TagResponse updateTag(
            @PathVariable Long id,
            @RequestBody @Valid TagUpdateRequest request
    ) {
        Tag updated = tagService.updateName(id, request.getName());
        return new TagResponse(updated.getId(), updated.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
    }
}
