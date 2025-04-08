package com.tedd.service;

import com.google.protobuf.Descriptors;
import com.tedd.*;
import com.tedd.dto.SecurityDto;
import com.tedd.dto.SecurityTypeDto;
import com.tedd.dto.mappers.SecurityMapper;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SecuritiesClientService {

    @GrpcClient("grpc-tedd-service")
    SecurityServiceGrpc.SecurityServiceBlockingStub syncClient;

    @GrpcClient("grpc-tedd-service")
    SecurityServiceGrpc.SecurityServiceStub asyncClient;

    public List<SecurityDto> getAllSecurities() {
        Empty request = Empty.newBuilder().build();
        SecuritiesList response = syncClient.getAllSecurities(request);
        return response.getSecuritiesList().stream().map(SecurityMapper::toDto).toList();
    }

    public List<SecurityDto> getSecuritiesByCustodian(int custodianID) throws InterruptedException {

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Custodian custodianRequest = Custodian.newBuilder().setCustodianID(custodianID).build();
        List<SecurityDto> response = new ArrayList<>();

        asyncClient.getSecuritiesByCustodian(custodianRequest, new StreamObserver<Security>() {
            @Override
            public void onNext(Security security) {
                response.add(SecurityMapper.toDto(security));
            }

            @Override
            public void onError(Throwable throwable) {
                log.error("error occurred GRPC Service ");
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });

        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : new ArrayList<>();
    }
}
