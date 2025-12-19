# Bookstore gRPC Microservices Architecture

## Architecture Overview

```mermaid
graph TB
    subgraph "Client Layer"
        HTTP[HTTP Client<br/>Browser/Postman/curl]
    end
    
    subgraph "Service Consumer<br/>(Port 8081)"
        REST[BooksController<br/>REST API]
        CLIENT[BookClientService<br/>gRPC Client]
        MAPPER[BookMapper<br/>DTO Converter]
    end
    
    subgraph "gRPC Interface"
        PROTO[schema.proto<br/>Protocol Definitions]
    end
    
    subgraph "Service Provider<br/>(gRPC Server)"
        SERVER[PublisherBookService<br/>gRPC Service]
        DB[dummyBookstoreDB<br/>In-Memory Database]
    end
    
    HTTP -->|HTTP GET| REST
    REST -->|Calls| CLIENT
    CLIENT -->|gRPC Call| SERVER
    SERVER -->|Query| DB
    DB -->|Returns Books| SERVER
    SERVER -->|gRPC Response| CLIENT
    CLIENT -->|Maps to DTO| MAPPER
    MAPPER -->|BookDto| CLIENT
    CLIENT -->|Returns| REST
    REST -->|JSON Response| HTTP
    
    PROTO -.->|Generates| CLIENT
    PROTO -.->|Generates| SERVER
```

## Sequence Diagram - GetAllBooks (Synchronous)

```mermaid
sequenceDiagram
    participant Client as HTTP Client
    participant Controller as BooksController
    participant ClientService as BookClientService<br/>(Blocking Stub)
    participant ServerService as PublisherBookService
    participant DB as dummyBookstoreDB
    
    Client->>Controller: GET /api/books
    Controller->>ClientService: getAllBooks()
    ClientService->>ServerService: gRPC: getAllBooks(Empty)
    ServerService->>DB: getBooksFromTempDb()
    DB-->>ServerService: List<Book>
    ServerService->>ServerService: Build BooksList
    ServerService-->>ClientService: BooksList (gRPC Response)
    ClientService->>ClientService: Map Book → BookDto
    ClientService-->>Controller: List<BookDto>
    Controller-->>Client: HTTP 200 OK (JSON)
```

## Sequence Diagram - GetBooksByPublisher (Asynchronous Streaming)

```mermaid
sequenceDiagram
    participant Client as HTTP Client
    participant Controller as BooksController
    participant ClientService as BookClientService<br/>(Async Stub)
    participant Observer as StreamObserver
    participant ServerService as PublisherBookService
    participant DB as dummyBookstoreDB
    
    Client->>Controller: GET /api/books/publisher/{id}
    Controller->>ClientService: getBooksByPublisher(publisherId)
    ClientService->>ClientService: Create CountDownLatch
    ClientService->>ClientService: Build Publisher request
    ClientService->>Observer: Create StreamObserver
    ClientService->>ServerService: gRPC: getBooksByPublisher(Publisher)
    
    ServerService->>DB: getBooksFromTempDb()
    DB-->>ServerService: List<Book>
    
    loop For each matching book
        ServerService->>Observer: onNext(Book)
        Observer->>ClientService: Add BookDto to list
    end
    
    ServerService->>Observer: onCompleted()
    Observer->>ClientService: CountDownLatch.countDown()
    ClientService->>ClientService: await(1 minute)
    ClientService->>ClientService: Map Book → BookDto
    ClientService-->>Controller: List<BookDto>
    Controller-->>Client: HTTP 200 OK (JSON)
```

## Component Interaction Flow

```mermaid
flowchart LR
    subgraph "Request Flow"
        A[HTTP Request] --> B[BooksController]
        B --> C{Endpoint?}
        C -->|GET /api/books| D[getAllBooks<br/>Synchronous]
        C -->|GET /api/books/publisher/{id}| E[getBooksByPublisher<br/>Asynchronous]
    end
    
    subgraph "gRPC Communication"
        D --> F[Blocking Stub<br/>Request-Response]
        E --> G[Async Stub<br/>Streaming]
        F --> H[PublisherBookService]
        G --> H
    end
    
    subgraph "Data Layer"
        H --> I[dummyBookstoreDB]
        I --> J[In-Memory Books]
    end
    
    subgraph "Response Flow"
        J --> H
        H --> F
        H --> G
        F --> K[BookMapper]
        G --> K
        K --> L[BookDto]
        L --> M[HTTP Response]
    end
```

## gRPC Service Methods

```mermaid
graph TD
    subgraph "BookService (gRPC)"
        A[getAllBooks<br/>Empty → BooksList<br/>Synchronous]
        B[getBooksByPublisher<br/>Publisher → stream Book<br/>Asynchronous Streaming]
        C[addBooks<br/>BooksList → StatusResponse<br/>Future Implementation]
    end
    
    A --> D[Returns all books<br/>in one response]
    B --> E[Streams books<br/>one by one]
    C --> F[Adds books<br/>to database]
```

## Data Transformation Flow

```mermaid
graph LR
    subgraph "gRPC Layer"
        A[Book<br/>Proto Message]
    end
    
    subgraph "DTO Layer"
        B[BookDto<br/>Java Object]
    end
    
    subgraph "HTTP Layer"
        C[JSON Response]
    end
    
    A -->|BookMapper.toDto| B
    B -->|Spring Serialization| C
    
    style A fill:#e1f5ff
    style B fill:#fff4e1
    style C fill:#e8f5e9
```

## Technology Stack

```mermaid
mindmap
  root((Bookstore gRPC<br/>Microservices))
    Service Consumer
      Spring Boot
      Spring Web
      gRPC Client
      REST API
    Service Provider
      Spring Boot
      gRPC Server
      In-Memory DB
    gRPC Interface
      Protocol Buffers
      Java Code Generation
    Communication
      HTTP/REST
      gRPC
      JSON
      Protobuf
```

