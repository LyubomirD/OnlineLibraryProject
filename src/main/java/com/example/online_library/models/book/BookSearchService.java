package com.example.online_library.models.book;

import com.example.online_library.exceptions.BookNotFoundException;
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

    public List<Book> viewAllSearchBooks(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);

        if (bookList.isEmpty()) {

            throw new BookNotFoundException("Book with that title does not exists");
        }

        return bookList;
    }

}
