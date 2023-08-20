package com.swiz.bcs.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CopyDTO {
    private Long bookId;
    private String location;
    private String status;

    // getters and setters
}
