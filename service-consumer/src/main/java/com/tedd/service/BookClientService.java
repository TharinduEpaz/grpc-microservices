package com.tedd.service;

import com.google.protobuf.Descriptors;
import com.tedd.*;
import com.tedd.dto.BookDto;
import com.tedd.dto.BookGenreDto;
import com.tedd.dto.mappers.BookMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Client service for interacting with the Books gRPC service.
 * Provides methods to retrieve books data from the remote service
 * using both synchronous and asynchronous gRPC communication patterns.
 */
@Slf4j
@Service
public class BookClientService {

    /**
     * Synchronous blocking stub for making gRPC calls to the book service.
     * Used for simple request-response patterns.
     */
    @GrpcClient("grpc-tedd-service")
    BookServiceGrpc.BookServiceBlockingStub syncClient;

    /**
     * Asynchronous non-blocking stub for making gRPC calls to the book service.
     * Used for streaming responses and non-blocking operations.
     */
    @GrpcClient("grpc-tedd-service")
    BookServiceGrpc.BookServiceStub asyncClient;

    /**
     * Retrieves all books from the gRPC service.
     * Uses synchronous blocking call pattern.
     *
     * @return List of BookDto objects containing all books data
     */
    public List<BookDto> getAllBooks() {
        Empty request = Empty.newBuilder().build();
        BooksList response = syncClient.getAllBooks(request);
        return response.getBooksList().stream().map(BookMapper::toDto).toList();
    }

    /**
     * Retrieves books filtered by publisher ID from the gRPC service.
     * Uses asynchronous streaming call pattern with a countdown latch to wait for completion.
     *
     * @param publisherID The ID of the publisher to filter books by
     * @return List of BookDto objects matching the publisher ID, or empty list if timeout occurs
     * @throws InterruptedException If the waiting process is interrupted
     */
    public List<BookDto> getBooksByPublisher(int publisherID) throws InterruptedException {

        // CountDownLatch is a synchronization aid that allows one or more threads to wait until
        // a set of operations being performed in other threads completes.
        // Here we initialize it with count=1, meaning we'll wait for exactly one countdown event.
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Publisher publisherRequest = Publisher.newBuilder().setPublisherID(publisherID).build();
        List<BookDto> response = new ArrayList<>();

        asyncClient.getBooksByPublisher(publisherRequest, new StreamObserver<Book>() {
            @Override
            public void onNext(Book book) {
                response.add(BookMapper.toDto(book));
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error occurred GRPC Service ");
                // Signal completion (with error) by decrementing the latch counter
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                // Signal normal completion by decrementing the latch counter
                countDownLatch.countDown();
            }
        });

        // Wait for the asynchronous operation to complete, with a timeout of 1 minute
        // This prevents the method from hanging indefinitely if the gRPC service doesn't respond
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        // Return the collected books if completed within timeout, otherwise return empty list
        return await ? response : new ArrayList<>();
    }
}

