package com.example.online_library.user_borrows_book;

import com.example.online_library.models.categories.Category;
import lombok.*;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BorrowedBookRequest {
    private String title;
    private String author;
    private String coAuthor;
    private Set<Category> category;

}
