package com.smartrecipe.service;

import com.smartrecipe.entity.Tag;
import com.smartrecipe.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findOrCreateByName(String name) {
        return tagRepository.findByNameIgnoreCase(name).orElseGet(() -> {
                    Tag newTag = Tag.builder()
                            .name(name.trim().toLowerCase())
                            .build();
                    return tagRepository.save(newTag);
                });
    }

    public Tag createTag(Tag tag) {
        String normalizedName = tag.getName().trim().toLowerCase();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be empty");
        }

        if (tagRepository.findByNameIgnoreCase(normalizedName).isPresent()) {
            throw new IllegalArgumentException("Tag already exists: " + normalizedName);
        }

        tag.setName(normalizedName);
        return tagRepository.save(tag);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag updateName(Long id, String newName) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + id));

        String normalizedName = newName.trim().toLowerCase();

        Optional<Tag> existing = tagRepository.findByNameIgnoreCase(normalizedName);
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Tag name already exists: " + normalizedName);
        }

        tag.setName(normalizedName);
        return tagRepository.save(tag);
    }

    public void deleteTag(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag not found with id: " + id);
        }

        // TODO: Validar que no esté siendo usado en recetas
        //if (recipeTagRepository.existsByTagId(id)) {
        //     throw new IllegalStateException("Cannot delete tag in use");
        // }

        tagRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return tagRepository.findByNameIgnoreCase(name).isPresent();
    }

}
