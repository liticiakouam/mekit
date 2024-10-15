package com.team.mekit.controller;

import com.team.mekit.dto.ProductDto;
import com.team.mekit.entities.Role;
import com.team.mekit.entities.User;
import com.team.mekit.exception.ResourceNotFoundException;
import com.team.mekit.repository.RoleRepository;
import com.team.mekit.response.ApiResponse;
import com.team.mekit.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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

}
