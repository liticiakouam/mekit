package com.team.mekit.repository;

import com.team.mekit.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndBrand(String name, String brand);

    List<Product> findByCategoryName(String category);

    List<Product> findByNameOrBrand(String name, String brand);

    List<Product> findAllBySellerId(Long id);
}
