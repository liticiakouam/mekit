package com.team.mekit.controller;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Product;
import com.team.mekit.response.ApiResponse;
import com.team.mekit.service.click.IClickService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/clicks")
public class ClickController {

    private final IClickService clickService;

    @GetMapping("/user/{userId}/product/{productId}/redirect")
    public ResponseEntity<Void> trackClickAndRedirect(
            @PathVariable Long userId,
            @PathVariable Long productId) {

        // Enregistrer le clic
        clickService.incrementClick(userId);

        // Construire l'URL de redirection vers le produit
        String productUrl = "http://localhost:9191/api/products/product/" + productId + "/product"; // URL vers le produit

        // Rediriger l'utilisateur
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(productUrl))
                .build();
    }

    @GetMapping("/point-stat")
    public ResponseEntity<ApiResponse> calculateClickPoint(){
        return  ResponseEntity.ok(new ApiResponse("success", clickService.calculateClickPoint()));
    }
}
