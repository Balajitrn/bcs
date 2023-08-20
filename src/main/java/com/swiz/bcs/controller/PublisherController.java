package com.swiz.bcs.controller;


import com.swiz.bcs.dto.PublisherDTO;
import com.swiz.bcs.entity.Publisher;
import com.swiz.bcs.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public Publisher createPublisher(@RequestBody PublisherDTO publisher) {
        return publisherService.savePublisher(publisher);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.findAllPublishers();
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable Long id) {
        return publisherService.findPublisherById(id);
    }

    // Other endpoints related to publishers
}

