# GRPC Spring Boot Implementation

This project is a simple implementation of a GRPC service using Spring Boot.


### Build the grpc interface

```
cd grpc-interface
mvn clean install
```

note : grpc interface shoud be build before building the spring boot application to the proto files are generated.

### API Endpoints to retrive data from the grpc consumer

[GET]
http://localhost:8081/api/securities

<br><br>

[GET]
http://localhost:8081/api/securities/custodian/1

Note : the  grpc consumer will call the grpc provider to get the data.
this project has both Synchronous and Asynchronous implementation of the grpc consumer.