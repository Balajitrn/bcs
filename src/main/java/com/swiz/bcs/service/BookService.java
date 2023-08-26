package com.swiz.bcs.service;


import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Author;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/***
 *
 * API will now support CRUD operations:
 *
 * Create: POST /api/books
 * Read: GET /api/books and GET /api/books/{id}
 * Update: PUT /api/books/{id}
 * Delete: DELETE /api/books/{id}
 *
 * The updateBook method in BookService looks for the book in the repository using its ID and
 * throws an exception if the book isn't found. It then updates the existing book with the
 * provided data and saves it.
 * The deleteBook method simply removes the book with the given ID from the repository.
 */

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper mapper;

    public BookDTO saveBook(BookDTO book) {
        logger.info("book: {} is being saved to the database",book.getTitle());
        Book bookDetails = mapper.toEntity(book);
        Book savedBook = bookRepository.save(bookDetails);
        return mapper.toDTO(savedBook);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> findBooksByGenre(Long genreId) {
        // Implement query to find books by genre
        return bookRepository.findBooksByGenre(genreId);

    }

    public List<Book> findBooksByAuthor(Long authorId) {
        // Implement query to find books by author
        return null;
    }

    public List<Book> findBooksByRating(Double rating) {
        // Implement query to find books by rating
        return null;
    }

    public BookDTO updateBook(Long id, BookDTO updatedBook) {
        Book existingBook = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
        // Here you can update the existing book with the updatedBook's fields
        // e.g., existingBook.setTitle(updatedBook.getTitle());
        // ...
        Book bookSearch = mapper.toEntity(updatedBook);
        existingBook.setAuthor(bookSearch.getAuthor());
        existingBook.setTitle(updatedBook.getTitle());
        Book updatedEntity = bookRepository.save(existingBook);
        return mapper.toDTO(updatedEntity);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    // Other book-related operations

    /**
     *
     * @param keyword
     * @return List of book that matches the search keyword
     */
    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return bookRepository.findAll();
        }
        return bookRepository.search(keyword);
    }
}
