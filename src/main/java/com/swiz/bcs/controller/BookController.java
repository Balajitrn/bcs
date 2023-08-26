package com.swiz.bcs.controller;

import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO book) {
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    /**
     *
     * @param genreId
     * @return: list of books by genre
     */
    @GetMapping("/genre/{genreId}")
    public List<Book> getBooksByGenre(@PathVariable Long genreId) {
        return bookService.findBooksByGenre(genreId);
    }


    /**
     *
     * @param keyword
     * @return controller layer to list all the books based on the keyword value
     * https://localhost/api/books/search/
     */
    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(required = false) String keyword) {
        return bookService.searchBooks(keyword);
    }

    /**
     *
     * @param id
     * @param updatedBook
     * @return
     */

    // Other endpoints related to books
    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBook) {
        return bookService.updateBook(id, updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }



}

