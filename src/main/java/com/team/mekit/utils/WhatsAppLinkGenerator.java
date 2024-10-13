package com.team.mekit.utils;

import com.team.mekit.entities.Image;
import com.team.mekit.entities.Product;
import com.team.mekit.entities.User;
import com.team.mekit.repository.ProductRepository;
import com.team.mekit.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WhatsAppLinkGenerator {

    private final ProductRepository productRepository;
    private final IUserService iUserService;

    private static final String BASE_WHATSAPP_URL = "https://wa.me/";

    public String generateProductShareLink(Long productId) {

        Product product = productRepository.findById(productId).get();
        User user = iUserService.getAuthenticatedUser();
        String productUrl = "http://localhost:9191/api/products/" + productId + "/share-on-whatsApp";
        //String productUrl = "https://monapp.com/produit/" + productId;
        String message = "Découvrez ce produit : " + product.getName() +
                " au prix imbattable de "  + product.getPrice() +
                "  - " + productUrl +generateImageUrls(product.getImages());

        // Encoder le message en URL
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        return BASE_WHATSAPP_URL + user.getPhoneNumber() + "?text=" + encodedMessage;
    }

    private String generateImageUrls(List<Image> images) {
        // Parcourir et concaténer toutes les URLs des images
        return images.stream()
                .map(Image::getUrl)
                .collect(Collectors.joining("\n"));
    }
}
