package com.team.mekit.service.click;

import com.team.mekit.entities.Click;
import com.team.mekit.entities.Product;
import com.team.mekit.entities.User;
import com.team.mekit.repository.ClickRepository;
import com.team.mekit.repository.ProductRepository;
import com.team.mekit.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClickService implements IClickService {

    private final ClickRepository clickRepository;
    private  final IUserService iUserService;
    private final ProductRepository productRepository;

    @Override
    public void incrementClick(Long userId) {
        User user = iUserService.findById(userId);

        // Vérifie si un enregistrement existe déjà
        Optional<Click> optionalClick = clickRepository.findByUserId(userId);

        if (optionalClick.isEmpty()) {
            Click newClick = new Click();
            newClick.setUser(user);
            newClick.setClickCount(newClick.getClickCount() + 1);
            clickRepository.save(newClick);
        } else {
            Click click = optionalClick.get();
            click.setClickCount(click.getClickCount() + 1);
            clickRepository.save(click);
        }

    }

    @Override
    public int calculateClickPoint() {
        User user = iUserService.getAuthenticatedUser();

        Click click = clickRepository.findByUserId(user.getId()).get();
        return click.getClickCount() * 2;
    }
}
