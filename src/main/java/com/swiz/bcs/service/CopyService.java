package com.swiz.bcs.service;


import com.swiz.bcs.dto.CopyDTO;
import com.swiz.bcs.entity.Copy;
import com.swiz.bcs.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyService {

    @Autowired
    private CopyRepository copyRepository;

    public Copy saveCopy(CopyDTO copyDTO) {
        Copy copy = Copy.builder().build();

        return copyRepository.save(copy);
    }

    public List<Copy> findAllCopies() {
        return copyRepository.findAll();
    }

    public Copy findCopyById(Long id) {
        return copyRepository.findById(id).orElse(null);
    }

    // Other copy-related operations
}

