package com.swiz.bcs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GenreDTO {
    private String name;
    private String description;

    // getters and setters
}
