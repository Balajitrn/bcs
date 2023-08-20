package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    // You can define custom queries here if needed
}
