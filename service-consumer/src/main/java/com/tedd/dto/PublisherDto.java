package com.tedd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublisherDto {
    private int publisherId;
    private String publisherName;
    private String country;
    private List<BookDto> books;
    private String rating;
}

