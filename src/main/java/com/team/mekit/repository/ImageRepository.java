package com.team.mekit.repository;

import com.team.mekit.entities.Category;
import com.team.mekit.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
