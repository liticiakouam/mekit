package com.team.mekit.request;

import com.team.mekit.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateASellerRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String city;
    private String address;
    private String companyName;
}
