package com.team.mekit.controller;

import com.team.mekit.dto.ImageDto;
import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Image;
import com.team.mekit.entities.Product;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.exception.ResourceNotFoundException;
import com.team.mekit.request.AddProductRequest;
import com.team.mekit.request.ProductUpdateRequest;
import com.team.mekit.response.ApiResponse;
import com.team.mekit.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<ProductDto> productDtos = productService.getAllProductsDto();
        return  ResponseEntity.ok(new ApiResponse("success", productDtos));
    }


    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            ProductDto product = productService.getProductById(productId);
            return  ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@ModelAttribute AddProductRequest product,
                                                  @RequestParam("files") List<MultipartFile> files) {
        try {
            Product theProduct = productService.addProduct(product, files);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Add product success!", productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Conflit!", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
        }

    }

    @PutMapping("/product/{productId}/update")
    public  ResponseEntity<ApiResponse> updateProduct(@ModelAttribute ProductUpdateRequest request, @PathVariable Long productId, @RequestParam("files") List<MultipartFile> files) {
        try {
            ProductDto productDto = productService.updateProduct(productId, request, files);
            return ResponseEntity.ok(new ApiResponse("Update product success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Product not found!", e.getMessage()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
