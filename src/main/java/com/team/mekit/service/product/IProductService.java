package com.team.mekit.service.product;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Product;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.request.AddProductRequest;
import com.team.mekit.request.ProductUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest request, List<MultipartFile> file) throws AlreadyExistsException, IOException;
    ProductDto getProductById(Long id);
    void deleteProductById(Long id);

    ProductDto updateProduct(Long id, ProductUpdateRequest updatedProductDto, List<MultipartFile> newImages) throws IOException;
    List<ProductDto> getConvertedProducts(List<Product> products);

    List<Product> getProductsByCategory(String category);

    List<Product> findProductsByNameOrBrand(String name);

    List<Product> getAllProductsForAuthUser();

    List<ProductDto> getAllProductsDto();

    ProductDto convertToDto(Product theProduct);

    int getNumberOfProductIsRecommandedForASeller (Long sellerId);
    int getNumberOfProductIsRecommandedByAutUser (Long recommanderId);
}
