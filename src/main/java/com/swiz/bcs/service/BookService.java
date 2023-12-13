package com.swiz.bcs.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Author;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.entity.BookStatus;
import com.swiz.bcs.entity.Reservation;
import com.swiz.bcs.exception.BookNotFoundException;
import com.swiz.bcs.repository.BookRepository;
import com.swiz.bcs.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
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
    private static final String SECRET = "ourSecretKey"; // @TODO later replace with the correct secret key

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookMapper mapper;

    public BookDTO saveBook(BookDTO book) {
        logger.info("book: {} is being saved to the database at info level",book.getTitle());
        logger.warn("book: {} is being saved to the database at warn level",book.getTitle());
        logger.info("book: "+book.getTitle()+ "is being saved to the database at warn level");
        Book bookDetails = mapper.toEntity(book);
        Book savedBook = bookRepository.save(bookDetails);
        return mapper.toDTO(savedBook);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: "+id));
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
        BookDTO bookDTO =  new BookDTO();
        try{
            logger.info("trying to update the book");
            Book existingBook = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book not found"));
            // Here you can update the existing book with the updatedBook's fields
            // e.g., existingBook.setTitle(updatedBook.getTitle());
            // ...
            Book bookSearch = mapper.toEntity(updatedBook);
            existingBook.setAuthor(bookSearch.getAuthor());
            existingBook.setTitle(updatedBook.getTitle());
            Book updatedEntity = bookRepository.save(existingBook);
            return mapper.toDTO(updatedEntity);
        }catch (Exception e){

            logger.error("exception occured inside update book, please review the error {}",e.getMessage());
            e.printStackTrace();
        }
        return bookDTO;
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

    /**
     *
     * @param jwtToken
     * @return true when token validation is success or else return false
     */
    public boolean validateJwtToken(String jwtToken) {
        // validate the JWT token
        // this can include decoding the JWT and checking the issuer, expiration etc
        /**
         * 1. Add the dependency for AuthO
         * 2. create a secret key
         * 3. Decode the JWT and check the expiry date
         * 4. Checking the issuer
         */

        try{
            // Using HMAC256 algorithm to verify the token
            Algorithm algorithm = Algorithm.HMAC256(SECRET);

            //verify the token
            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(jwtToken);

            // check the expiry
            Date expiresAt = jwt.getExpiresAt();
            if(expiresAt.before(new Date())){
                return false;
            }

            //check the issuer
            Claim issuerClaim = jwt.getClaim("issuer");
            if (issuerClaim == null) {
                return false;
            }
                return  true;
        }catch(JWTVerificationException e){
        //invalid token
            return false;
            }
        }


    /**
     *
     * @param bookId
     * @return true when reservation of the book is successful else return false
     */
    public boolean reserveBook(Long bookId, Long userId) {

        /**
         * 1. find if the book is available by querying the DB
         * 2. update the status to "reserved"
         * 3. tag the userid with reservation
         */

        Book book = bookRepository.findById(bookId).orElse(null);


        if(book != null && BookStatus.AVAILABLE.equals(book.getStatus())){

            try{
                book.setStatus(BookStatus.RESERVED);
                bookRepository.save(book);
                // create a new reservation entry
                Reservation reservation = new Reservation();
                reservation.setBookId(bookId);
                reservation.setUserId(userId);
                reservation.setReservationDate(new Date());
                reservationRepository.save(reservation);
                return true;
            } catch (RuntimeException ex){
                logger.error("exception {} occured while saving the book",ex.getMessage());
                throw new RuntimeException("failed to save Reservation"+ex.getMessage());
            }

        }
        return false;
    }
}
