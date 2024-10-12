package com.team.mekit.repository;

import com.team.mekit.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String defaultEmail);
}
