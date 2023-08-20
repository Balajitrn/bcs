package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // You can define custom queries here if needed
}
