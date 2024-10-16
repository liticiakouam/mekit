package com.team.mekit.service.user;

import com.team.mekit.dto.RecomDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.User;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.request.CreateARecommanderRequest;
import com.team.mekit.request.CreateASellerRequest;

import java.util.List;

public interface IUserService {
    User getAuthenticatedUser();
    List<User> getAllUsers();
    SellerDto getAuthenticatedUserInfoForSeller();
    RecomDto getAuthenticatedUserInfoForRecom();
    void createARecommander(CreateARecommanderRequest recommanderRequest) throws AlreadyExistsException;
    void createASeller(CreateASellerRequest sellerRequest) throws AlreadyExistsException;

}
