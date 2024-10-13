package com.team.mekit.dto;


import com.team.mekit.entities.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
