package com.tedd.dto.mappers;

import com.tedd.Publisher;
import com.tedd.Book;
import com.tedd.dto.PublisherDto;
import com.tedd.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PublisherMapper {



    public PublisherDto toDto(Publisher publisher) {
        if (publisher == null) {
            return null;
        }

        List<BookDto> booksDto = publisher.getBooksList().stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());

        return PublisherDto.builder()
                .publisherId(publisher.getPublisherID())
                .publisherName(publisher.getPublisherName())
                .country(publisher.getCountry())
                .books(booksDto)
                .rating(publisher.getRating())
                .build();
    }

    public Publisher toGrpc(PublisherDto dto) {
        if (dto == null) {
            return null;
        }

        Publisher.Builder builder = Publisher.newBuilder()
                .setPublisherID(dto.getPublisherId())
                .setPublisherName(dto.getPublisherName())
                .setCountry(dto.getCountry())
                .setRating(dto.getRating());

        if (dto.getBooks() != null) {
            dto.getBooks().forEach(bookDto ->
                    builder.addBooks(BookMapper.toGrpc(bookDto)));
        }

        return builder.build();
    }
}

