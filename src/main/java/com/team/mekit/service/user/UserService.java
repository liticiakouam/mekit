package com.team.mekit.service.user;

import com.team.mekit.dto.RecomDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.User;
import com.team.mekit.repository.RoleRepository;
import com.team.mekit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.team.mekit.utils.ConvertToDto.convertToRecomDto;
import static com.team.mekit.utils.ConvertToDto.convertToSellerDto;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User getAuthenticatedUser() {
        return userRepository.findById(5L).get();
    }

    @Override
    public SellerDto getAuthenticatedUserInfoForSeller() {
        User user = getAuthenticatedUser();
        return convertToSellerDto(user);
    }

    @Override
    public RecomDto getAuthenticatedUserInfoForRecom() {
        User user = getAuthenticatedUser();
        return convertToRecomDto(user);
    }
}
