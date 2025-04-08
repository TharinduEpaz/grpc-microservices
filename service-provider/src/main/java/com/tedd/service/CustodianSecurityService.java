package com.tedd.service;

import com.tedd.*;
import com.tedd.dummyDB.dummyWealthManagementDB;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * gRPC service implementation for handling security-related operations.
 * This service provides methods to retrieve securities information from the wealth management database.
 */
@GrpcService
public class CustodianSecurityService extends SecurityServiceGrpc.SecurityServiceImplBase {
    
    /**
     * Retrieves all securities from the database.
     *
     * @param request Empty request object
     * @param responseObserver Stream observer to send the response containing all securities
     */
    @Override
    public void getAllSecurities(Empty request, StreamObserver<SecuritiesList> responseObserver) {
        SecuritiesList response = SecuritiesList.newBuilder()
                .addAllSecurities(dummyWealthManagementDB.getSecuritiesFromTempDb()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Retrieves securities filtered by a specific custodian.
     *
     * @param request Custodian object containing the custodian ID to filter by
     * @param responseObserver Stream observer to send the filtered securities one by one
     */
    @Override
    public void getSecuritiesByCustodian(Custodian request, StreamObserver<Security> responseObserver) {
        dummyWealthManagementDB.getSecuritiesFromTempDb()
                .stream()
                .filter(security -> security.getCustodianID() == request.getCustodianID())
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
