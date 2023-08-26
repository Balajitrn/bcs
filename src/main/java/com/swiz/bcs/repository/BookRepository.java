package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByGenre(Long genreId);
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorNameContainingIgnoreCase(String authorName);
    List<Book> findByGenreNameContainingIgnoreCase(String genreName);
    // You can define custom queries here if needed

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.author.name LIKE %:keyword% OR b.genre.name LIKE %:keyword%")
    List<Book> search(String keyword);
}
