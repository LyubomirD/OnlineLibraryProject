package com.example.online_library.library.user;

import com.example.online_library.library.LibraryRequest;
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

    public List<LibraryRequest> viewAllBookByTitle(String title) {
        List<Book> bookList = bookSearchService.viewAllSearchBooks(title);

        return getLibraryRequests(bookList);
    }

    public List<LibraryRequest> viewAllBooks() {
        List<Book> bookList = bookSearchService.viewAllBooks();

        return getLibraryRequests(bookList);
    }

    private List<LibraryRequest> getLibraryRequests(List<Book> bookList) {
        List<LibraryRequest> libraryRequests = new ArrayList<>();

        for (Book book : bookList) {
            LibraryRequest request = new LibraryRequest(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCoAuthor(),
                    book.getCategories()
            );

            libraryRequests.add(request);
        }

        return libraryRequests;
    }

}
