package com.team.mekit.service.user;

import com.team.mekit.entities.User;
import com.team.mekit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public User getAuthenticatedUser() {
        User user = User.builder().id(3L).firstName("Anz").lastName("LK").email("Anz@com").build();
        if (userRepository.findAll().isEmpty()){
            userRepository.save(user);
        }
        return user;
    }
}
