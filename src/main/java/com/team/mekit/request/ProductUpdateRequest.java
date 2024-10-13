package com.team.mekit.request;

import com.team.mekit.entities.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private Category category;
}
