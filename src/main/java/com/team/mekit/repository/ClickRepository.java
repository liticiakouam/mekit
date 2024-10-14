package com.team.mekit.repository;

import com.team.mekit.entities.Click;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClickRepository extends JpaRepository<Click, Long> {
    Optional<Click> findByUserId(Long userId);
}