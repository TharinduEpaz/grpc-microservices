//package com.tedd.service;
//
//import com.google.protobuf.Descriptors;
//import com.tedd.Author;
//import com.tedd.Book;
//import com.tedd.bookAuthorServiceGrpc;
//import com.tedd.dto.AuthorResponse;
//import com.tedd.dto.mappers.AuthorMapper;
//import net.devh.boot.grpc.client.inject.GrpcClient;
//import org.springframework.stereotype.Service;
//
//import java.util.Map;
//
//@Service
//public class BookAuthorClientService {
//
//    @GrpcClient("grpc-tedd-service")
//    bookAuthorServiceGrpc.bookAuthorServiceBlockingStub syncClient;
//
//    public AuthorResponse getAuthor(int authorID ) {
//
//        Author request = Author.newBuilder().setAuthorID(authorID).build();
//        Author authorResponse = syncClient.getBookAuthor(request);
//
//        return AuthorMapper.mapAuthorToResponse(authorResponse);
//    }
//}
