package com.swiz.bcs.service;


import com.swiz.bcs.dto.PublisherDTO;
import com.swiz.bcs.entity.Publisher;
import com.swiz.bcs.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher savePublisher(PublisherDTO publisherDTO) {
        Publisher publisher = Publisher.builder()
                .address(publisherDTO.getAddress())
                .name(publisherDTO.getName())
                .build();
        return publisherRepository.save(publisher);
    }

    public List<Publisher> findAllPublishers() {
        return publisherRepository.findAll();
    }

    public Publisher findPublisherById(Long id) {
        return publisherRepository.findById(id).orElse(null);
    }

    // Other publisher-related operations
}
