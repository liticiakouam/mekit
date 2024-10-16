package com.team.mekit.repository;

import com.team.mekit.entities.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    int countRecommendationBySellerId(Long userId);

    int countRecommendationByRecommenderId(Long recommenderId);
}