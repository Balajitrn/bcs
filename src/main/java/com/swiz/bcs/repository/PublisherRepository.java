package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    // You can define custom queries here if needed
}
