package com.team.mekit.utils;

import com.team.mekit.dto.RecomDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.User;
import lombok.Data;

@Data
public class ConvertToDto {

    public static SellerDto convertToSellerDto(User user) {
        if (user == null) {
            return null; // Gestion des cas où le produit est null
        }

        return new SellerDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCity(),
                user.getAddress(),
                user.getCompanyName()
        );
    }

    public static RecomDto convertToRecomDto (User user) {
        if (user == null) {
            return null; // Gestion des cas où le produit est null
        }

        return new RecomDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getCity(),
                user.getAddress()
        );
    }
}
