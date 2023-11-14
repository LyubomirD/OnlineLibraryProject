package com.example.demo.library;

import com.example.demo.models.book.Book;
import com.example.demo.models.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;


import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryService {

    private final BookService bookService;

    public void includeNewBookToLibrary(LibraryRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);

        if (isAdmin) {
            bookService.addBookToLibrary(
                    new Book(
                            request.getTitle(),
                            request.getAuthor(),
                            request.getCoAuthor(),
                            request.getGenre()
                    )
            );
        } else {
            try {
                throw new AccessDeniedException("User is not an ADMIN");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<LibraryRequest> viewAllBookByTitle(String title) {
        List<Book> bookList = bookService.viewAllSearchBooks(title);

        List<LibraryRequest> libraryRequests = new ArrayList<>();

        for (Book book : bookList) {
            LibraryRequest request = new LibraryRequest(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoAuthor(),
                    book.getGenre()
            );

            libraryRequests.add(request);
        }

        return libraryRequests;
    }

    public List<LibraryRequest> viewAllBooks() {
        List<Book> bookList = bookService.viewAllBooks();

        List<LibraryRequest> libraryRequests = new ArrayList<>();

        for (Book book : bookList) {
            LibraryRequest request = new LibraryRequest(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoAuthor(),
                    book.getGenre()
            );

            libraryRequests.add(request);
        }

        return libraryRequests;
    }
}
