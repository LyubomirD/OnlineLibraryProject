package com.example.demo.user_borrows_book;

import lombok.Data;

import java.util.List;

@Data
public class BorrowedBookRequest {
    private List<Long> bookIds;
}
