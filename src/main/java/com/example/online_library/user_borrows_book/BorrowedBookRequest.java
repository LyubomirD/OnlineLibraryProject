package com.example.online_library.user_borrows_book;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BorrowedBookRequest {
    private String title;
    private String author;
}
