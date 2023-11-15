package com.example.demo.library.admin;

import com.example.demo.models.categories.Category;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LibraryAdminRequest {
    private final String title;
    private final String author;
    private final String coAuthor;
    private final Set<Category> category;
}
