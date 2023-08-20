package com.swiz.bcs.repository;

import com.swiz.bcs.entity.Copy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CopyRepository extends JpaRepository<Copy, Long> {
    // You can define custom queries here if needed
}
