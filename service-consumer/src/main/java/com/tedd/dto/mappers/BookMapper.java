package com.tedd.dto.mappers;

import com.tedd.Book;
import com.tedd.dto.BookDto;
import com.tedd.dto.BookGenreDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public static BookDto toDto(Book book) {
        if (book == null) {
            return null;
        }

        return BookDto.builder()
                .bookId(book.getBookID())
                .publisherId(book.getPublisherID())
                .bookName(book.getBookName())
                .genre(BookGenreDto.fromGrpc(book.getGenre()))
                .price(book.getPrice())
                .stock(book.getStock())
                .isbn(book.getIsbn())
                .build();
    }

    public static Book toGrpc(BookDto dto) {
        if (dto == null) {
            return null;
        }

        return Book.newBuilder()
                .setBookID(dto.getBookId())
                .setPublisherID(dto.getPublisherId())
                .setBookName(dto.getBookName())
                .setGenre(dto.getGenre().toGrpc())
                .setPrice(dto.getPrice())
                .setStock(dto.getStock())
                .setIsbn(dto.getIsbn())
                .build();
    }
}

