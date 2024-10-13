package com.team.mekit.service.product;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Product;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.request.AddProductRequest;
import com.team.mekit.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product) throws AlreadyExistsException;
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);

    //ProductDto convertToDto(Product product);
}
