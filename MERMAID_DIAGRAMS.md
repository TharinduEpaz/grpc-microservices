# Mermaid Diagrams for Bookstore gRPC Project

## 1. Complete Data Flow Diagram

```mermaid
sequenceDiagram
    participant Client as HTTP Client
    participant REST as BooksController<br/>(REST API)
    participant ClientService as BookClientService<br/>(gRPC Client)
    participant ServerService as PublisherBookService<br/>(gRPC Server)
    participant DB as dummyBookstoreDB
    
    Note over Client,DB: Synchronous Flow - GetAllBooks
    Client->>REST: GET /api/books
    REST->>ClientService: getAllBooks()
    ClientService->>ServerService: gRPC: getAllBooks(Empty)
    ServerService->>DB: getBooksFromTempDb()
    DB-->>ServerService: List<Book>
    ServerService-->>ClientService: BooksList (gRPC Response)
    ClientService->>ClientService: Map Book → BookDto
    ClientService-->>REST: List<BookDto>
    REST-->>Client: HTTP 200 OK (JSON)
    
    Note over Client,DB: Asynchronous Flow - GetBooksByPublisher
    Client->>REST: GET /api/books/publisher/1
    REST->>ClientService: getBooksByPublisher(1)
    ClientService->>ClientService: Create CountDownLatch & StreamObserver
    ClientService->>ServerService: gRPC: getBooksByPublisher(Publisher)
    ServerService->>DB: getBooksFromTempDb()
    DB-->>ServerService: List<Book>
    loop For each matching book
        ServerService->>ClientService: Stream: onNext(Book)
        ClientService->>ClientService: Add BookDto to list
    end
    ServerService->>ClientService: onCompleted()
    ClientService->>ClientService: await(1 min timeout)
    ClientService-->>REST: List<BookDto>
    REST-->>Client: HTTP 200 OK (JSON)
```

## 2. Architecture Overview

```mermaid
graph TB
    subgraph "Client Layer"
        HTTP[HTTP Client]
    end
    
    subgraph "Service Consumer :8081"
        REST[BooksController<br/>REST Endpoints]
        CLIENT[BookClientService<br/>gRPC Client Stubs]
        MAPPER[BookMapper<br/>Proto ↔ DTO]
    end
    
    subgraph "gRPC Communication"
        GRPC[gRPC Protocol<br/>Port 9090]
    end
    
    subgraph "Service Provider"
        SERVER[PublisherBookService<br/>gRPC Server]
        DB[dummyBookstoreDB<br/>In-Memory Data]
    end
    
    HTTP -->|HTTP GET| REST
    REST -->|Method Call| CLIENT
    CLIENT -->|Blocking/Async Stub| GRPC
    GRPC -->|gRPC Call| SERVER
    SERVER -->|Query| DB
    DB -->|List<Book>| SERVER
    SERVER -->|gRPC Response| GRPC
    GRPC -->|BooksList/Stream| CLIENT
    CLIENT -->|Map| MAPPER
    MAPPER -->|BookDto| CLIENT
    CLIENT -->|Return| REST
    REST -->|JSON| HTTP
```

## 3. Synchronous vs Asynchronous gRPC Calls

```mermaid
graph TB
    subgraph "Synchronous - getAllBooks"
        A1[HTTP Request] --> A2[BooksController]
        A2 --> A3[BookClientService.getAllBooks]
        A3 --> A4[Blocking Stub]
        A4 --> A5[gRPC: getAllBooks]
        A5 --> A6[PublisherBookService]
        A6 --> A7[Returns BooksList]
        A7 --> A8[Single Response]
        A8 --> A9[HTTP Response]
    end
    
    subgraph "Asynchronous - getBooksByPublisher"
        B1[HTTP Request] --> B2[BooksController]
        B2 --> B3[BookClientService.getBooksByPublisher]
        B3 --> B4[Async Stub + StreamObserver]
        B4 --> B5[gRPC: getBooksByPublisher]
        B5 --> B6[PublisherBookService]
        B6 --> B7[Streams Books]
        B7 --> B8[Multiple onNext calls]
        B8 --> B9[onCompleted]
        B9 --> B10[CountDownLatch.await]
        B10 --> B11[HTTP Response]
    end
```

## 4. Component Details

```mermaid
classDiagram
    class BooksController {
        +getAllBooks() List~BookDto~
        +getBooksByPublisher(int) List~BookDto~
    }
    
    class BookClientService {
        -syncClient: BlockingStub
        -asyncClient: Stub
        +getAllBooks() List~BookDto~
        +getBooksByPublisher(int) List~BookDto~
    }
    
    class PublisherBookService {
        +getAllBooks(Empty, StreamObserver)
        +getBooksByPublisher(Publisher, StreamObserver)
    }
    
    class BookMapper {
        +toDto(Book) BookDto
        +toGrpc(BookDto) Book
    }
    
    class dummyBookstoreDB {
        +getBooksFromTempDb() List~Book~
        +getPublishersFromTempDb() List~Publisher~
    }
    
    BooksController --> BookClientService
    BookClientService --> BookMapper
    BookClientService --> PublisherBookService
    PublisherBookService --> dummyBookstoreDB
```

## 5. Request-Response Flow (Simplified)

```mermaid
flowchart TD
    Start([HTTP Request]) --> Endpoint{Which Endpoint?}
    
    Endpoint -->|/api/books| Sync[getAllBooks<br/>Synchronous]
    Endpoint -->|/api/books/publisher/{id}| Async[getBooksByPublisher<br/>Asynchronous]
    
    Sync --> SyncGrpc[gRPC Blocking Call]
    SyncGrpc --> SyncServer[Server: getAllBooks]
    SyncServer --> SyncDB[DB: Get All Books]
    SyncDB --> SyncResponse[Return BooksList]
    SyncResponse --> SyncMap[Map to BookDto]
    SyncMap --> SyncJSON[JSON Response]
    
    Async --> AsyncGrpc[gRPC Async Call]
    AsyncGrpc --> AsyncServer[Server: getBooksByPublisher]
    AsyncServer --> AsyncDB[DB: Filter by Publisher]
    AsyncDB --> AsyncStream[Stream Books]
    AsyncStream --> AsyncCollect[Collect in StreamObserver]
    AsyncCollect --> AsyncMap[Map to BookDto]
    AsyncMap --> AsyncJSON[JSON Response]
    
    SyncJSON --> End([HTTP 200 OK])
    AsyncJSON --> End
```

## 6. Data Model Flow

```mermaid
graph LR
    subgraph "Proto Layer"
        P1[Book.proto]
        P2[Publisher.proto]
        P3[BookGenre enum]
    end
    
    subgraph "Generated Java"
        J1[Book.java]
        J2[Publisher.java]
        J3[BookGenre.java]
    end
    
    subgraph "DTO Layer"
        D1[BookDto]
        D2[PublisherDto]
        D3[BookGenreDto]
    end
    
    subgraph "JSON Response"
        JSON[{"bookId": 1,<br/>"bookName": "...",<br/>"genre": "FICTION"}]
    end
    
    P1 --> J1
    P2 --> J2
    P3 --> J3
    J1 -->|BookMapper| D1
    J2 -->|PublisherMapper| D2
    J3 -->|BookMapper| D3
    D1 --> JSON
    D2 --> JSON
    D3 --> JSON
```

