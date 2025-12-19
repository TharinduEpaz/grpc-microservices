package com.tedd.controller;

import com.google.protobuf.Descriptors;
import com.tedd.BooksList;
import com.tedd.dto.BookDto;
import com.tedd.service.BookClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookClientService bookClientService;

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        log.info("Received request to get all books");
        try {
            return ResponseEntity.ok(bookClientService.getAllBooks());
        } catch (Exception e) {
            log.error("Error retrieving all books", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<List<BookDto>> getBooksByPublisher(
            @PathVariable("publisherId") int publisherId) {
        log.info("Received request to get books for publisher ID: {}", publisherId);
        try {
            List<BookDto> books =
                    bookClientService.getBooksByPublisher(publisherId);

            if (books.isEmpty()) {
                log.info("No books found for publisher ID: {}", publisherId);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(books);
        } catch (InterruptedException e) {
            log.error("Interrupted while fetching books for publisher ID: {}", publisherId, e);
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error retrieving books for publisher ID: {}", publisherId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

