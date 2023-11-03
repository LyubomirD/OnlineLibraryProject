package com.example.demo.user_borrows_book;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BorrowedBookRequest {
    private String email;
    private String title;
    private String author;
}
