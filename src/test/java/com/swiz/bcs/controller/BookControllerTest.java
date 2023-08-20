package com.swiz.bcs.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiz.bcs.dto.BookDTO;
import com.swiz.bcs.entity.Book;
import com.swiz.bcs.entity.BookStatus;
import com.swiz.bcs.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author: Balaji
 * This test class makes use of Spring's WebMvcTest and MockMvc classes
 * to set up a test environment that focuses solely on the web layer,
 * isolating the controller from other parts of the application.
 * The MockBean annotation is used to create a mock implementation of the BookService class,
 * and Mockito's when method is used to define its behavior.
 * */

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;

    @BeforeEach
    public void setup() {
        bookDTO = BookDTO.builder()
                .authorId(1L)
                .publicationDate(new Date(2005-07-16))
                .summary("a test book")
                .coverImage("a cover image")
                .ISBN("1234567")
                .genreId(1L)
                .publisherId(1L)
                .rating(5.0)
                .status("AVAILABLE")
                .title("Sample Book")
                .build();

        book = new Book(); // set up your entity here
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.saveBook(bookDTO)).thenReturn(bookDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJson = objectMapper.writeValueAsString(bookDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(book);
        when(bookService.findAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists()); // add more detailed assertions if needed
    }

    @Test
    public void testGetBookById() throws Exception {
        Long id = 1L;
        when(bookService.findBookById(id)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books/" + id))
                .andExpect(status().isOk());
        // add more detailed assertions based on the expected response
    }

    // Add similar test methods for other endpoints
}
