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

/**
 * Client service for interacting with the Securities gRPC service.
 * Provides methods to retrieve securities data from the remote service
 * using both synchronous and asynchronous gRPC communication patterns.
 */
@Slf4j
@Service
public class SecuritiesClientService {

    /**
     * Synchronous blocking stub for making gRPC calls to the security service.
     * Used for simple request-response patterns.
     */
    @GrpcClient("grpc-tedd-service")
    SecurityServiceGrpc.SecurityServiceBlockingStub syncClient;

    /**
     * Asynchronous non-blocking stub for making gRPC calls to the security service.
     * Used for streaming responses and non-blocking operations.
     */
    @GrpcClient("grpc-tedd-service")
    SecurityServiceGrpc.SecurityServiceStub asyncClient;

    /**
     * Retrieves all securities from the gRPC service.
     * Uses synchronous blocking call pattern.
     *
     * @return List of SecurityDto objects containing all securities data
     */
    public List<SecurityDto> getAllSecurities() {
        Empty request = Empty.newBuilder().build();
        SecuritiesList response = syncClient.getAllSecurities(request);
        return response.getSecuritiesList().stream().map(SecurityMapper::toDto).toList();
    }

    /**
     * Retrieves securities filtered by custodian ID from the gRPC service.
     * Uses asynchronous streaming call pattern with a countdown latch to wait for completion.
     *
     * @param custodianID The ID of the custodian to filter securities by
     * @return List of SecurityDto objects matching the custodian ID, or empty list if timeout occurs
     * @throws InterruptedException If the waiting process is interrupted
     */
    public List<SecurityDto> getSecuritiesByCustodian(int custodianID) throws InterruptedException {

        // CountDownLatch is a synchronization aid that allows one or more threads to wait until
        // a set of operations being performed in other threads completes.
        // Here we initialize it with count=1, meaning we'll wait for exactly one countdown event.
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
                // Signal completion (with error) by decrementing the latch counter
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                // Signal normal completion by decrementing the latch counter
                countDownLatch.countDown();
            }
        });

        // Wait for the asynchronous operation to complete, with a timeout of 1 minute
        // This prevents the method from hanging indefinitely if the gRPC service doesn't respond
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        // Return the collected securities if completed within timeout, otherwise return empty list
        return await ? response : new ArrayList<>();
    }
}
