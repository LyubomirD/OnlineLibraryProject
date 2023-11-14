package com.example.demo.models.categories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findOrCreateCategory(String genre) {
        Optional<Category> existingCategory = categoryRepository.findByGenre(genre);

        return existingCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setGenre(genre);
            return categoryRepository.save(newCategory);
        });
    }
}
