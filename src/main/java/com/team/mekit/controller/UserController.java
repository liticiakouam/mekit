package com.team.mekit.controller;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Product;
import com.team.mekit.entities.Role;
import com.team.mekit.entities.User;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.exception.ResourceNotFoundException;
import com.team.mekit.repository.RoleRepository;
import com.team.mekit.request.AddProductRequest;
import com.team.mekit.request.CreateARecommanderRequest;
import com.team.mekit.request.CreateASellerRequest;
import com.team.mekit.response.ApiResponse;
import com.team.mekit.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/auth-user")
    public ResponseEntity<ApiResponse> getAuthenticatedUserInfos() {
        try {
            User user = userService.getAuthenticatedUser();
            Role recommander = roleRepository.findByName("ROLE_RECOMMANDER").get();
            Role seller = roleRepository.findByName("ROLE_SELLER").get();

            if (user.getRoles().contains(seller)) {
                return  ResponseEntity.ok(new ApiResponse("success", userService.getAuthenticatedUserInfoForSeller()));
            }
            return  ResponseEntity.ok(new ApiResponse("success", userService.getAuthenticatedUserInfoForRecom()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/recommander/add")
    public ResponseEntity<ApiResponse> createRecommander(@RequestBody CreateARecommanderRequest recommanderRequest) {
        try {
            userService.createARecommander(recommanderRequest);
            return ResponseEntity.ok(new ApiResponse("Recommander created successfuly!", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), "Conflit!"));
        }

    }

    @PostMapping("/seller/add")
    public ResponseEntity<ApiResponse> createSeller(@RequestBody CreateASellerRequest sellerRequest) {
        try {
            userService.createASeller(sellerRequest);
            return ResponseEntity.ok(new ApiResponse("Seller created successfuly!", null));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Conflit!", e.getMessage()));
        }

    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<User> users = userService.getAllUsers();
        return  ResponseEntity.ok(new ApiResponse("success", users));
    }

}
