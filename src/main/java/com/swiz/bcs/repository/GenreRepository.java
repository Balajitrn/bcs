package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    // You can define custom queries here if needed
}

