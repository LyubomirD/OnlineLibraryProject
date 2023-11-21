package com.example.online_library.models.categories;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findOrCreateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByGenreIgnoreCase(category.getGenre());

        return existingCategory.orElseGet(() -> {
            Category newCategory = new Category();
            newCategory.setGenre(category.getGenre());
            return categoryRepository.save(newCategory);
        });
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }
}
