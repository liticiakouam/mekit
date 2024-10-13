package com.team.mekit.repository;

import com.team.mekit.entities.Image;
import com.team.mekit.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndBrand(String name, String brand);
}
