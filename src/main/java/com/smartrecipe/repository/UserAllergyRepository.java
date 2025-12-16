package com.smartrecipe.repository;

import com.smartrecipe.entity.UserAllergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAllergyRepository extends JpaRepository<UserAllergy, Long> {

    List<UserAllergy> findByUserId(Long userId);

    boolean existsByAllergyId(Long allergyId);

    boolean existsByUserIdAndAllergyId(Long userId, Long allergyId);
}
