package com.team.mekit.controller;

import com.team.mekit.dto.ProductDto;
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

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return  ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            //ProductDto productDto = productService.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("success", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Add product success!", productDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public  ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product theProduct = productService.updateProduct(request, productId);
            ProductDto productDto = productService.convertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Update product success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
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
