package com.team.mekit.dto;


import com.team.mekit.entities.Category;
import com.team.mekit.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String description;
    private UserDto user;
    private Category category;
    private List<String> imageUrls;


}
