package com.swiz.bcs.service;


import com.swiz.bcs.dto.AuthorDTO;
import com.swiz.bcs.entity.Author;
import com.swiz.bcs.exception.AuthorNotFoundException;
import com.swiz.bcs.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(AuthorDTO author) {
        Author authorDetails = new Author();
        authorDetails.setName(author.getName());
        authorDetails.setBiography(author.getBiography());
        authorDetails.setProfileImage(author.getProfileImage());
        return authorRepository.save(authorDetails);
    }

    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with ID: "+id));
    }

    // Other author-related operations
}

