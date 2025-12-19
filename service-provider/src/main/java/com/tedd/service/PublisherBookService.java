package com.tedd.service;

import com.tedd.*;
import com.tedd.dummyDB.dummyBookstoreDB;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * gRPC service implementation for handling book-related operations.
 * This service provides methods to retrieve books information from the bookstore database.
 */
@GrpcService
public class PublisherBookService extends BookServiceGrpc.BookServiceImplBase {
    
    /**
     * Retrieves all books from the database.
     *
     * @param request Empty request object
     * @param responseObserver Stream observer to send the response containing all books
     */
    @Override
    public void getAllBooks(Empty request, StreamObserver<BooksList> responseObserver) {
        BooksList response = BooksList.newBuilder()
                .addAllBooks(dummyBookstoreDB.getBooksFromTempDb()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Retrieves books filtered by a specific publisher.
     *
     * @param request Publisher object containing the publisher ID to filter by
     * @param responseObserver Stream observer to send the filtered books one by one
     */
    @Override
    public void getBooksByPublisher(Publisher request, StreamObserver<Book> responseObserver) {
        dummyBookstoreDB.getBooksFromTempDb()
                .stream()
                .filter(book -> book.getPublisherID() == request.getPublisherID())
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
