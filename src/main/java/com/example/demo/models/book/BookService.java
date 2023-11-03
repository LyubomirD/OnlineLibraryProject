package com.example.demo.models.book;

import com.example.demo.exceptions.BookNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public void addBookToLibrary(Book book) {
        boolean bookExisting = bookRepository
                .findByTitleAndAuthor(book.getTitle(), book.getAuthor())
                .isPresent();

        if (bookExisting) {
            throw new RuntimeException("Book exists");
        }

        bookRepository.save(book);
    }


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
}
