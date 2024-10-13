package com.team.mekit.request;


import com.team.mekit.dto.ImageDto;
import com.team.mekit.entities.Category;
import com.team.mekit.entities.Image;
import com.team.mekit.entities.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String description;
    private User user;
    private String category;
    private List<MultipartFile> images;
}
