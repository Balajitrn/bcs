/****
 * This test case covers the positive scenario where everything goes as expected.
 * we can create additional test cases to cover exceptional scenarios,
 * such as when the book repository throws an exception
 */

package com.swiz.bcs.service;

import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.entity.BookStatus;
import com.swiz.bcs.entity.Reservation;
import com.swiz.bcs.repository.BookRepository;
import com.swiz.bcs.repository.ReservationRepository;
import com.swiz.bcs.service.BookMapper;
import com.swiz.bcs.service.BookService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeAll
    public static void setupBeforeAll(){
        // setup method to be used among the test methods
        System.out.println("inside the Before All");
    }

    @BeforeEach
    public void setup(){
        // setup method to be used among the test methods
        System.out.println("inside the Before Each");
    }



    @Test
    public void testSaveBook() {
        // Arrange
        BookDTO bookDTO = new BookDTO(/* params */);
        Book bookEntity = new Book(/* params */);
        Book savedBookEntity = new Book(/* params */);

        when(bookMapper.toEntity(bookDTO)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(savedBookEntity);
        when(bookMapper.toDTO(savedBookEntity)).thenReturn(bookDTO);

        // Act
        BookDTO result = bookService.saveBook(bookDTO);

        // Assert
        assertEquals(bookDTO, result);
        verify(bookMapper).toEntity(bookDTO);
        verify(bookRepository).save(bookEntity);
        verify(bookMapper).toDTO(savedBookEntity);
    }

    @Test
    public void test_reserveBook_whenAvailable() {
        //setup
        Long  bookId = 1L;
        Long  userId = 1L;
        Date  resevationDate = new Date();
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.AVAILABLE);

        //mock the repo
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // execute
        boolean result = bookService.reserveBook(bookId,userId);

        //Assertion
        assertTrue(result);
        assertEquals(BookStatus.RESERVED,book.getStatus());

        //verify
        verify(bookRepository,times(1)).save(book);
        verify(reservationRepository,times(1)).save(any(Reservation.class));
    }

    @Test
    public void test_reservation_when_book_notAvailable(){
        //setup
        Long  bookId = 1L;
        Long  userId = 1L;
        Date  resevationDate = new Date();
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.BORROWED);

        //mock the repo
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(new Reservation());

        // execute
        boolean result = bookService.reserveBook(bookId,userId);

        //Assertion
        assertFalse(result);
        assertEquals(BookStatus.BORROWED,book.getStatus());


        //verify
         verify(bookRepository,never()).save(book);
         verify(reservationRepository,never()).save(any(Reservation.class));

    }

    @Test
    public void when_unable_to_save_book(){
        //setup
        Long  bookId = 1L;
        Long  userId = 1L;
        Date  resevationDate = new Date();
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.AVAILABLE);

        //mock
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doThrow(new RuntimeException("failed to save Reservation")).when(bookRepository).save(book);

        //execute and assert exception
        try{
            boolean result = bookService.reserveBook(bookId,userId);
            assertFalse(result);
        }catch (RuntimeException ex){
            // logic to handle
        }

        //Assertion
        assertEquals(BookStatus.RESERVED,book.getStatus());


        //verify
        verify(bookRepository,times(1)).findById(bookId);
        verify(bookRepository,times(1)).save(book);
        verify(reservationRepository,never()).save(any());

    }
}
