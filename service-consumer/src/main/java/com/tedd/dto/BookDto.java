package com.tedd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private int bookId;
    private int publisherId;
    private String bookName;
    private BookGenreDto genre;
    private float price;
    private int stock;
    private String isbn;
}

