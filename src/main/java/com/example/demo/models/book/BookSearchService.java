package com.example.demo.models.book;

import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.models.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;

    public List<Book> viewAllSearchBooks(String title) {
        List<Book> bookList = bookRepository.findByTitle(title);

        if (bookList.isEmpty()) {

            throw new BookNotFoundException("Book with that title does not exists");
        }

        return bookList;
    }

    public Optional<Book> findBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    public List<Book> viewAllBooks() {
        return bookRepository.findAll();
    }
}
