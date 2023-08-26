/****
 * This test case covers the positive scenario where everything goes as expected.
 * we can create additional test cases to cover exceptional scenarios,
 * such as when the book repository throws an exception
 */

package com.swiz.bcs.service;

import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.repository.BookRepository;
import com.swiz.bcs.service.BookMapper;
import com.swiz.bcs.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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
}
