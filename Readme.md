# GRPC Spring Boot Implementation - Bookstore Application

This project is a simple implementation of a GRPC service using Spring Boot for a bookstore application.


### Build the grpc interface

```
cd grpc-interface
mvn clean install
```

note : grpc interface should be built before building the spring boot application so the proto files are generated.

### API Endpoints to retrieve data from the grpc consumer

[GET]
http://localhost:8081/api/books
<br>
[GET]
http://localhost:8081/api/books/publisher/1

Note : the grpc consumer will call the grpc provider to get the data.
This project has both Synchronous and Asynchronous implementation of the grpc consumer.

### Application Structure

- **grpc-interface**: Contains the proto file definitions for Book, Publisher, and BookGenre
- **service-provider**: gRPC server that provides book data from a dummy database
- **service-consumer**: REST API that consumes the gRPC service and exposes HTTP endpoints

### Entities

- **Book**: Represents a book with fields like bookID, publisherID, bookName, genre, price, stock, and ISBN
- **Publisher**: Represents a book publisher with fields like publisherID, publisherName, country, and rating
- **BookGenre**: Enum representing book genres (FICTION, NON_FICTION, SCI_FI, MYSTERY, ROMANCE, FANTASY)