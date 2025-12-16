package com.smartrecipe.service;

import com.smartrecipe.entity.Allergy;
import com.smartrecipe.repository.AllergyRepository;
import com.smartrecipe.repository.UserAllergyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AllergyService {

    private final AllergyRepository allergyRepository;
    private final UserAllergyRepository userAllergyRepository;

    public AllergyService(AllergyRepository allergyRepository, UserAllergyRepository userAllergyRepository) {
        this.allergyRepository = allergyRepository;
        this.userAllergyRepository = userAllergyRepository;
    }


    public Allergy findOrCreateByName(String name) {
        return allergyRepository.findByNameIgnoreCase(name).orElseGet(() -> {
                    Allergy newAllergy = Allergy.builder()
                            .name(name.trim())
                            .build();
                    return allergyRepository.save(newAllergy);
                });
    }

    public Allergy createAllergy(Allergy allergy) {
        String normalizedName = allergy.getName().trim();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Allergy name cannot be empty");
        }

        if (allergyRepository.findByNameIgnoreCase(normalizedName).isPresent()) {
            throw new IllegalArgumentException("Allergy already exists: " + normalizedName);
        }

        allergy.setName(normalizedName);
        return allergyRepository.save(allergy);
    }


    @Transactional(readOnly = true)
    public Optional<Allergy> findById(Long id) {
        return allergyRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Allergy> findByName(String name) {
        return allergyRepository.findByNameIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Allergy> findAll() {
        return allergyRepository.findAll();
    }

    public Allergy updateName(Long id, String newName) {
        Allergy allergy = allergyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Allergy not found with id: " + id));

        String normalizedName = newName.trim();

        Optional<Allergy> existing = allergyRepository.findByNameIgnoreCase(normalizedName);
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Allergy name already exists: " + normalizedName);
        }

        allergy.setName(normalizedName);
        return allergyRepository.save(allergy);
    }


    public void deleteAllergy(Long id) {
        if (!allergyRepository.existsById(id)) {
            throw new IllegalArgumentException("Allergy not found with id: " + id);
        }


        if (userAllergyRepository.existsByAllergyId(id)) {
            throw new IllegalStateException("Cannot delete allergy in use");
        }

        allergyRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return allergyRepository.findByNameIgnoreCase(name).isPresent();
    }
}