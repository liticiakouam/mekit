package com.team.mekit.service.click;

import com.team.mekit.entities.Click;
import com.team.mekit.entities.User;
import com.team.mekit.repository.ClickRepository;
import com.team.mekit.service.user.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClickService implements IClickService {

    private final ClickRepository clickRepository;
    private  final IUserService iUserService;

    @Override
    public void incrementClick(Long userId) {
        User user = iUserService.getAuthenticatedUser();
        // Vérifie si un enregistrement existe déjà
        Click click = clickRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Click newClick = new Click();
                    newClick.setUser(user); // Créer un nouvel utilisateur si nécessaire
                    newClick.setClickCount(1);
                    return newClick;
                });

        // Incrémente le compteur de clics
        click.setClickCount(click.getClickCount() + 1);
        clickRepository.save(click);
    }
}
