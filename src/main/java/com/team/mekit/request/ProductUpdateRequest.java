package com.team.mekit.request;

import com.team.mekit.dto.ImageDto;
import com.team.mekit.entities.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private Double price;
    private String description;
    private String category;
    private List<MultipartFile> images;
}
