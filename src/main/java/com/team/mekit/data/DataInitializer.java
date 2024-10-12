package com.team.mekit.data;


import com.team.mekit.entities.Category;
import com.team.mekit.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultCategoryIfNotExits();
    }

    private void createDefaultCategoryIfNotExits(){
            Category category1 = Category.builder().name("Vetements").build();
            Category category2 = Category.builder().name("Chaussures").build();
            categoryRepository.saveAll(List.of(category1, category2));
            System.out.println("Default category created successfully.");
    }


}