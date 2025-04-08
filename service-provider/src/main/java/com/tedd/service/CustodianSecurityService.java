package com.tedd.service;

import com.tedd.*;
import com.tedd.dummyDB.dummyWealthManagementDB;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class CustodianSecurityService extends SecurityServiceGrpc.SecurityServiceImplBase {
    @Override
    public void getAllSecurities(Empty request, StreamObserver<SecuritiesList> responseObserver) {
        SecuritiesList response = SecuritiesList.newBuilder()
                .addAllSecurities(dummyWealthManagementDB.getSecuritiesFromTempDb()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSecuritiesByCustodian(Custodian request, StreamObserver<Security> responseObserver) {
        dummyWealthManagementDB.getSecuritiesFromTempDb()
                .stream()
                .filter(security -> security.getCustodianID() == request.getCustodianID())
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
