package com.example.online_library.library.user;

import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookSearchService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LibraryUserService {

    private final BookSearchService bookSearchService;

    public List<LibraryUserRequest> viewAllBookByTitle(String title) {
        List<Book> bookList = bookSearchService.viewAllSearchBooks(title);

        return getLibraryRequests(bookList);
    }

    public List<LibraryUserRequest> viewAllBooks() {
        List<Book> bookList = bookSearchService.viewAllBooks();

        return getLibraryRequests(bookList);
    }

    private List<LibraryUserRequest> getLibraryRequests(List<Book> bookList) {
        List<LibraryUserRequest> libraryUserRequests = new ArrayList<>();

        for (Book book : bookList) {
            LibraryUserRequest request = new LibraryUserRequest(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoAuthor(),
                    book.getCategories()
            );

            libraryUserRequests.add(request);
        }

        return libraryUserRequests;
    }

}
