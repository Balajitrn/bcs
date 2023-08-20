package com.swiz.bcs.service;

import com.swiz.bcs.dto.GenreDTO;
import com.swiz.bcs.entity.Genre;
import com.swiz.bcs.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre saveGenre(GenreDTO genreDTO) {
        Genre genre = Genre.builder()
                .description(genreDTO.getDescription())
                .name(genreDTO.getName())
                .build();
        return genreRepository.save(genre);
    }

    public List<Genre> findAllGenres() {
        return genreRepository.findAll();
    }

    public Genre findGenreById(Long id) {
        return genreRepository.findById(id).orElse(null);
    }

    // Other genre-related operations
}

