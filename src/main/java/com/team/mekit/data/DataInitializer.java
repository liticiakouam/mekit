package com.team.mekit.data;


import com.team.mekit.entities.Category;
import com.team.mekit.entities.Product;
import com.team.mekit.entities.Role;
import com.team.mekit.entities.User;
import com.team.mekit.repository.CategoryRepository;
import com.team.mekit.repository.ProductRepository;
import com.team.mekit.repository.RoleRepository;
import com.team.mekit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles =  Set.of("ROLE_SELLER", "ROLE_RECOMMANDER");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultCategoryIfNotExits();
        createDefaultRecommenderIfNotExits();
        createDefaultSellerIfNotExits();
        createDefaultProductIfNotExits();
    }

    private void createDefaultCategoryIfNotExits(){
        if (categoryRepository.findAll().isEmpty() ) {
            Category category1 = Category.builder().name("Vetements").build();
            Category category2 = Category.builder().name("Chaussures").build();
            categoryRepository.saveAll(List.of(category1, category2));
            System.out.println("Default category created successfully.");
        }

    }


    private void createDefaultRecommenderIfNotExits(){
        Role userRole = roleRepository.findByName("ROLE_RECOMMANDER").get();
        for (int i = 1; i<=3; i++){
            String defaultEmail = "anz"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setPhoneNumber("237655131077");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }


    private void createDefaultSellerIfNotExits(){
        Role userRole = roleRepository.findByName("ROLE_SELLER").get();
        for (int i = 1; i<=2; i++){
            String defaultEmail = "sell"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("seller");
            user.setLastName("User" + i);
            user.setPhoneNumber("237655131077");
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role:: new).forEach(roleRepository::save);
        System.out.println("Default ROLE created successfully.");
    }

    private void createDefaultProductIfNotExits(){
        if (productRepository.findAll().isEmpty() ) {
            Category category1 = Category.builder().name("Vetements").build();
            User user = userRepository.findById(5L).get();
            Product product = new Product();
            product.setCategory(category1);
            product.setName("polo");
            product.setBrand("zara");
            product.setDescription("very nice");
            product.setPrice(5000.0);
            product.setSeller(user);

            productRepository.save(product);
            System.out.println("Default PRODUCT created successfully.");
        }

    }

}