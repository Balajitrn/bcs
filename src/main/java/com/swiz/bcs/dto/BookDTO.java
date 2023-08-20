package com.swiz.bcs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class BookDTO {
    private String title;
    private Long authorId;
    private String ISBN;
    private Long genreId;
    private Long publisherId;
    private Date publicationDate;
    private String summary;
    private String coverImage;
    private Double rating;
    private String status;

    // getters and setters
}
