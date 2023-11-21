package com.example.online_library.library.admin;

import com.example.online_library.exceptions.AdminAccessDeniedException;
import com.example.online_library.library.LibraryRequest;
import com.example.online_library.models.book.Book;
import com.example.online_library.models.book.BookAdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LibraryAdminService {

    //TODO uncomment the checkUserOrThrowException when frontend registration is complete
    private final BookAdminService bookAdminService;

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

    public Long getBookId(String title, String author) {
        //checkUserOrThrowException();

        return bookAdminService.findBookId(title, author);
    }

    public void includeNewBookToLibrary(LibraryRequest request) {
        //checkUserOrThrowException();

        bookAdminService.addNewBook(
                new Book(
                        request.getTitle(),
                        request.getAuthor(),
                        request.getCoAuthor(),
                        request.getCategory()
                )
        );
    }

    public void changeExistingBookInform(Long book_id, LibraryRequest request) {
        //checkUserOrThrowException();

        bookAdminService.updateExistingBook(
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
        //checkUserOrThrowException();

        bookAdminService.deleteBook(book_id);
    }
}
