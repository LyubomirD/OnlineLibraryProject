package com.example.online_library.library.user;

import com.example.online_library.models.categories.Category;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LibraryUserRequest {
    private final String title;
    private final String author;
    private final String coAuthor;
    private final Set<Category> category;
}
