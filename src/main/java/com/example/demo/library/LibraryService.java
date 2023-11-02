package com.example.demo.library;

import com.example.demo.models.book.Book;
import com.example.demo.models.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {

    private final BookService bookService;

    public void includeNewBook(LibraryRequest request) {
        bookService.addBookToLibrary(
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor()
                )
        );
    }

    public List<LibraryRequest> viewBook(String title) {
        List<Book> books = bookService.viewAllSearchBooks(title);

        List<LibraryRequest> libraryRequests = new ArrayList<>();

        for (Book book : books) {
            LibraryRequest request = new LibraryRequest(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoAuthor()
            );
            libraryRequests.add(request);
        }

        return libraryRequests;
    }
}
