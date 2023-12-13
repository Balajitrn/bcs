package com.swiz.bcs.controller;

import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    /**
     *
     * @param bookId
     * @param bearerToken
     * @return
     */

    @PostMapping("catalog/reserve/{bookId},{userId}")
    public ResponseEntity<String> reserveBook(
            @PathVariable Long bookId,
            @PathVariable Long userId,
            @RequestHeader("Authorization") String bearerToken){


        // Authorization -  "Bearer eyuasdasdasdasdmsfs678812njaklsdma883edsdddf333";
        // Extract the actual token from the bearer string
        String jwtToken = bearerToken.substring(7);

        // validate the JWT token
        if(!bookService.validateJwtToken(jwtToken)){
            return new ResponseEntity<>("Invalid Token",HttpStatus.UNAUTHORIZED);
        }

        if(!bookService.reserveBook(bookId,userId)){
            return new ResponseEntity<>("Failed to Reserve book", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Book reserved successfully", HttpStatus.OK);
    }

}

