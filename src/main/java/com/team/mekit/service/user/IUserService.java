package com.team.mekit.service.user;

import com.team.mekit.dto.RecomDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.User;

public interface IUserService {
    User getAuthenticatedUser();

    SellerDto getAuthenticatedUserInfoForSeller();

    RecomDto getAuthenticatedUserInfoForRecom();
}
