package com.swiz.bcs.service;


import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        return null;
    }

    public List<Book> findBooksByAuthor(Long authorId) {
        // Implement query to find books by author
        return null;
    }

    public List<Book> findBooksByRating(Double rating) {
        // Implement query to find books by rating
        return null;
    }

    // Other book-related operations
}
