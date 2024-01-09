package com.example.online_library.mapper.dto;

import com.example.online_library.models.categories.Category;
import lombok.Data;

import java.util.Set;

@Data
public class LibraryRequestDto {
    private final String title;
    private final String author;
    private final String coAuthor;
    private final Set<Category> category;
}
