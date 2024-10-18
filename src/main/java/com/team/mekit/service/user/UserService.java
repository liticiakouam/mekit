package com.team.mekit.service.user;

import com.team.mekit.dto.RecomDto;
import com.team.mekit.dto.SellerDto;
import com.team.mekit.entities.Role;
import com.team.mekit.entities.User;
import com.team.mekit.exception.AlreadyExistsException;
import com.team.mekit.exception.ResourceNotFoundException;
import com.team.mekit.repository.RoleRepository;
import com.team.mekit.repository.UserRepository;
import com.team.mekit.request.CreateARecommanderRequest;
import com.team.mekit.request.CreateASellerRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.team.mekit.utils.ConvertToDto.convertToRecomDto;
import static com.team.mekit.utils.ConvertToDto.convertToSellerDto;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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

    @Override
    public void createARecommander(CreateARecommanderRequest recommanderRequest) throws AlreadyExistsException {
        if (userRepository.existsByEmail(recommanderRequest.getEmail())) {
            throw new AlreadyExistsException("User already exist");
        }
        Role recommander = roleRepository.findByName("ROLE_RECOMMANDER").get();

        createRecommander(recommanderRequest, recommander);
    }

    private void createRecommander(CreateARecommanderRequest recommanderRequest, Role recommander) {

        User user = new User();
        user.setPhoneNumber(recommanderRequest.getPhoneNumber());
        user.setAddress(recommanderRequest.getAddress());
        user.setCity(recommanderRequest.getCity());
        user.setEmail(recommanderRequest.getEmail());
        user.setLastName(recommanderRequest.getLastName());
        user.setFirstName(recommanderRequest.getFirstName());
        user.setPassword(passwordEncoder.encode(recommanderRequest.getPassword()));
        user.setRoles(List.of(recommander));
        userRepository.save(user);
    }

    @Override
    public void createASeller(CreateASellerRequest sellerRequest) throws AlreadyExistsException {
        if (userRepository.existsByEmail(sellerRequest.getEmail())) {
            throw new AlreadyExistsException("User already exist");
        }

        User user = createSeller(sellerRequest);
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    private User createSeller(CreateASellerRequest sellerRequest) {
        Role seller = roleRepository.findByName("ROLE_SELLER").get();
        Role recommander = roleRepository.findByName("ROLE_RECOMMANDER").get();

        User user = new User();
        user.setPhoneNumber(sellerRequest.getPhoneNumber());
        user.setAddress(sellerRequest.getAddress());
        user.setCity(sellerRequest.getCity());
        user.setEmail(sellerRequest.getEmail());
        user.setLastName(sellerRequest.getLastName());
        user.setFirstName(sellerRequest.getFirstName());
        user.setPassword(passwordEncoder.encode(sellerRequest.getPassword()));
        user.setCompanyName(sellerRequest.getCompanyName());
        user.setRoles(List.of(seller, recommander));
        return user;
    }
}
