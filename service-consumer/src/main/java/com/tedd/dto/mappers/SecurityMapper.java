package com.tedd.dto.mappers;

import com.tedd.Security;
import com.tedd.dto.SecurityDto;
import com.tedd.dto.SecurityTypeDto;
import org.springframework.stereotype.Component;

@Component
public class SecurityMapper {

    public static SecurityDto toDto(Security security) {
        if (security == null) {
            return null;
        }

        return SecurityDto.builder()
                .securityId(security.getSecurityID())
                .custodianId(security.getCustodianID())
                .securityName(security.getSecurityName())
                .type(SecurityTypeDto.fromGrpc(security.getType()))
                .price(security.getPrice())
                .quantity(security.getQuantity())
                .symbol(security.getSymbol())
                .build();
    }

    public static Security toGrpc(SecurityDto dto) {
        if (dto == null) {
            return null;
        }

        return Security.newBuilder()
                .setSecurityID(dto.getSecurityId())
                .setCustodianID(dto.getCustodianId())
                .setSecurityName(dto.getSecurityName())
                .setType(dto.getType().toGrpc())
                .setPrice(dto.getPrice())
                .setQuantity(dto.getQuantity())
                .setSymbol(dto.getSymbol())
                .build();
    }
}