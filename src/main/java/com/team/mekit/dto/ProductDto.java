package com.team.mekit.dto;


import com.team.mekit.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String description;
    private SellerDto user;
    private Category category;
    private List<String> imageUrls;


}
