package com.team.mekit.service.product;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.Category;
import com.team.mekit.entities.Image;
import com.team.mekit.entities.Product;
import com.team.mekit.entities.User;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.exception.ResourceNotFoundException;
import com.team.mekit.repository.CategoryRepository;
import com.team.mekit.repository.ImageRepository;
import com.team.mekit.repository.ProductRepository;
import com.team.mekit.request.AddProductRequest;
import com.team.mekit.request.ProductUpdateRequest;
import com.team.mekit.service.user.IUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.team.mekit.utils.ConvertToDto.convertToSellerDto;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final IUserService iUserService;

    public static String IMAGE_UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images";


    @Override
    public Product addProduct(AddProductRequest request, List<MultipartFile> file) throws AlreadyExistsException, IOException {
        if (productExists(request.getName(), request.getBrand())) {
            throw new AlreadyExistsException(request.getBrand() + " "
                    + request.getName() + " already exists, you may update this product instead!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory()))
                .orElseGet(() -> {
                    throw new ResourceNotFoundException("Category not found ");
                });

        User user = iUserService.getAuthenticatedUser();

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setBrand(request.getBrand());
        product.setCategory(category);
        product.setUser(user);

        List<Image> images = new ArrayList<>();

        List<String> imagePaths = saveImages(file);

        for (String imagePath : imagePaths) {
            Image image = new Image();
            image.setUrl(imagePath);// Définit l'URL de l'image
            image.setProduct(product); // Associe l'image au produit
            images.add(image);
        }

        product.setImages(images);

        return productRepository.save(product);
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    private List<String> saveImages(List<MultipartFile> files) throws IOException {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(IMAGE_UPLOAD_DIR, fileName);

            // Créer le répertoire si nécessaire
            Files.createDirectories(path.getParent());

            // Transférer le fichier vers le chemin spécifié
            file.transferTo(path);

            // Ajouter le chemin d'accès de l'image à la liste
            imagePaths.add(path.toString());
        }

        return imagePaths; // Retourner la liste des chemins d'accès des images sauvegardées
    }


    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));

        // Créer une liste d'URL pour les images
        List<String> imageUrls = product.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        SellerDto sellerDto = convertToSellerDto(product.getUser());

        // Retourner le DTO avec les informations du produit et les images
        return new ProductDto(product.getId(), product.getName(), product.getBrand(), product.getPrice() ,product.getDescription(), sellerDto,product.getCategory(), imageUrls);
    }


    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    // Functional approach for category removal
                    Optional.ofNullable(product.getCategory())
                            .ifPresent(category -> category.getProducts().remove(product));
                    product.setCategory(null);
                    productRepository.delete(product);
                }, () -> {
                    throw new EntityNotFoundException("Product not found!");
                });
    }

    @Override
    public ProductDto updateProduct(Long id, ProductUpdateRequest updatedProductDto, List<MultipartFile> newImages) throws IOException {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
        Category category = Optional.ofNullable(categoryRepository.findByName(updatedProductDto.getCategory()))
                .orElseGet(() -> {
                    throw new ResourceNotFoundException("Category not found ");
                });
        // Mettre à jour les détails du produit
        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setDescription(updatedProductDto.getDescription());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setBrand(updatedProductDto.getBrand());
        existingProduct.setCategory(category);

        // Gérer la mise à jour des images

        // Supprimer les anciennes images si nécessaire
        List<Image> existingImages = existingProduct.getImages();
        existingImages.clear();

        // Si des nouvelles images sont fournies, les traiter
        if (newImages != null && !newImages.isEmpty()) {
            List<String> imagePaths = saveImages(newImages);

            for (String imagePath : imagePaths) {
                Image image = new Image();
                image.setUrl(imagePath);// Définit l'URL de l'image
                image.setProduct(existingProduct); // Associe l'image au produit
                existingImages.add(image);
            }
        }

        // Mettre à jour la liste des images du produit
        existingProduct.setImages(existingImages);

        // Sauvegarder le produit mis à jour
        productRepository.save(existingProduct);

        // Convertir le produit mis à jour en DTO et le retourner
        return convertToDto(existingProduct);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> findProductsByNameOrBrand(String name) {
        return productRepository.findByNameOrBrand(name, name);
    }


    @Override
    public List<Product> getAllProductsForAuthUser() {
        Long id = iUserService.getAuthenticatedUser().getId();
        return productRepository.findAllByUserId(id);
    }

    @Override
    public List<ProductDto> getAllProductsDto() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            List<String> imageUrls = new ArrayList<>();
            for (Image image : product.getImages()) {
                imageUrls.add(image.getUrl());
            }
            SellerDto sellerDto = convertToSellerDto(product.getUser());

            productDtos.add(new ProductDto(product.getId(), product.getName(), product.getBrand(), product.getPrice() , product.getDescription(), sellerDto,product.getCategory(), imageUrls));
        }
        return productDtos;
    }

    public ProductDto convertToDto(Product theProduct) {
        if (theProduct == null) {
            return null; // Gestion des cas où le produit est null
        }

        // Récupérer les URLs des images
        List<String> imageUrls = theProduct.getImages().stream()
                .map(Image::getUrl)
                .collect(Collectors.toList());

        SellerDto sellerDto = convertToSellerDto(theProduct.getUser());
        // Créer et retourner le ProductDto
        return new ProductDto(
                theProduct.getId(),
                theProduct.getName(),
                theProduct.getBrand(),
                theProduct.getPrice(),
                theProduct.getDescription(),
                sellerDto,
                theProduct.getCategory(),
                imageUrls
        );
    }



}
