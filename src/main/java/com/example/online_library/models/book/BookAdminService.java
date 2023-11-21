package com.example.online_library.models.book;

import com.example.online_library.exceptions.BookNotFoundException;
import com.example.online_library.models.categories.Category;
import com.example.online_library.models.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookAdminService {

    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    private boolean bookExists(Book book) {
        boolean bookExisting = bookRepository
                .findByTitleAndAuthor(book.getTitle(), book.getAuthor())
                .isPresent();

        if (bookExisting) {
            return true;
        }

        return false;
    }

    private void saveOrCreateCategory(Book book) {
        Set<Category> categories = new HashSet<>();

        for (Category category : book.getCategories()) {
            Category orCreateCategory = categoryService.findOrCreateCategory(category);
            categories.add(orCreateCategory);
        }

        book.setCategories(categories);
    }

    public Long findBookId(String title, String author) {
        Optional<Book> book = bookRepository.findByTitleAndAuthor(title, author);

        if (book.isEmpty()) {
            throw new BookNotFoundException("Book does not exists");
        }

        return book.get().getId();
    }

    public void addNewBook(Book book) {
        if (bookExists(book)) {
            throw new RuntimeException("Book exists");
        }

        saveOrCreateCategory(book);
        bookRepository.save(book);
    }

    public void updateExistingBook(Long book_id, Book updatedBook) {
        Book existingBook = bookRepository.findById(book_id).orElseThrow(
                () -> new EntityNotFoundException("Book not found with ID: " + book_id));

        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setCoAuthor(updatedBook.getCoAuthor());
        saveOrCreateCategory(updatedBook);

        existingBook.setCategories(updatedBook.getCategories());

        bookRepository.save(existingBook);
    }


    public void deleteBook(Long bookId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new RuntimeException("Book does NOT exists");
        }

        bookRepository.deleteById(bookId);
    }


    // Method needed from BorrowedBookService
    public Optional<Book> findBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

}
