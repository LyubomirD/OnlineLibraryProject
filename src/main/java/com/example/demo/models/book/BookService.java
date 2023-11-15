package com.example.demo.models.book;

import com.example.demo.exceptions.BookNotFoundException;
import com.example.demo.models.categories.Category;
import com.example.demo.models.categories.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BookService {

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

    private void categoriesSomething(Book book) {
        Set<Category> categories = new HashSet<>();

        for (Category category : book.getCategories()) {
            Category orCreateCategory = categoryService.findOrCreateCategory(category);
            categories.add(orCreateCategory);
        }

        book.setCategories(categories);
    }

    public void addNewBook(Book book) {
        if (bookExists(book)) {
            throw new RuntimeException("Book exists");
        }

        bookRepository.save(book);
    }

    public void updateExistingBook(Long book_id, Book book) {
        Book existingBook = bookRepository.findById(book_id).orElseThrow(
                () -> new EntityNotFoundException("Book not found with ID: " + book_id));

        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setCoAuthor(book.getCoAuthor());
        categoriesSomething(existingBook);

        bookRepository.save(existingBook);
    }

    public void deleteBook(Long bookId) {
        if (bookRepository.findById(bookId).isEmpty()) {
            throw new RuntimeException("Book does NOT exists");
        }

        bookRepository.deleteById(bookId);
    }

    public Optional<Book> findBookByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }
}
