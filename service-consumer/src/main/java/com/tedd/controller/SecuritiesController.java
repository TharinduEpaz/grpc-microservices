package com.tedd.controller;

import com.google.protobuf.Descriptors;
import com.tedd.SecuritiesList;
import com.tedd.dto.SecurityDto;
import com.tedd.service.SecuritiesClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/securities")
@RequiredArgsConstructor
public class SecuritiesController {

    private final SecuritiesClientService securitiesClientService;

    @GetMapping
    public ResponseEntity<List<SecurityDto>> getAllSecurities() {
        log.info("Received request to get all securities");
        try {
            return ResponseEntity.ok(securitiesClientService.getAllSecurities());
        } catch (Exception e) {
            log.error("Error retrieving all securities", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }

    @GetMapping("/custodian/{custodianId}")
    public ResponseEntity<List<SecurityDto>> getSecuritiesByCustodian(
            @PathVariable("custodianId") int custodianId) {
        log.info("Received request to get securities for custodian ID: {}", custodianId);
        try {
            List<SecurityDto> securities =
                    securitiesClientService.getSecuritiesByCustodian(custodianId);

            if (securities.isEmpty()) {
                log.info("No securities found for custodian ID: {}", custodianId);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(securities);
        } catch (InterruptedException e) {
            log.error("Interrupted while fetching securities for custodian ID: {}", custodianId, e);
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            log.error("Error retrieving securities for custodian ID: {}", custodianId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}