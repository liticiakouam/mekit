package com.team.mekit.repository;

import com.team.mekit.entities.Category;
import com.team.mekit.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
