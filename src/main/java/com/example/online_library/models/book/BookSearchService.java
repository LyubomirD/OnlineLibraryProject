package com.example.online_library.models.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;

    public List<Book> viewAllBooks() {
        return bookRepository.findAll();
    }

}
