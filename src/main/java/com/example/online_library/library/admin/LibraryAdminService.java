package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    private final BookService bookService;

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ADMIN"::equals);
    }

    private void checkUserOrThrowException() {
        if (!isAdmin()) {
            throw new AdminAccessDeniedException("Access denied, user not administrator");
        }
    }

    public void includeNewBookToLibrary(LibraryAdminRequest request) {
        checkUserOrThrowException();

        bookService.addNewBook(
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor(),
                        request.getCategory()
                )
        );
    }

    public void changeExistingBookInform(Long book_id, LibraryAdminRequest request) {
        checkUserOrThrowException();

        bookService.updateExistingBook(
                book_id,
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor(),
                        request.getCategory()
                )
        );
    }

    public void deleteBookFromLibrary(Long book_id) {
        checkUserOrThrowException();

        bookService.deleteBook(book_id);
    }



}
