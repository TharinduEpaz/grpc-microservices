package com.tedd.service;

import com.tedd.Author;
import com.tedd.Book;
import com.tedd.bookAuthorServiceGrpc;
import com.tedd.dummyDB.dummyBookDB;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class BookAuthorServerService extends bookAuthorServiceGrpc.bookAuthorServiceImplBase {

    @Override
    public void getBookAuthor(Author request, StreamObserver<Author> responseObserver) {
        dummyBookDB.getAuthorsFromTempDb()
                .stream()
                .filter(author -> author.getAuthorID() == request.getAuthorID())
                .findFirst()
                .ifPresent(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
