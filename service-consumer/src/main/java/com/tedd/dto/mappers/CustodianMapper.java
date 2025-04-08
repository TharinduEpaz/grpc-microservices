package com.tedd.dto.mappers;

import com.tedd.Custodian;
import com.tedd.Security;
import com.tedd.dto.CustodianDto;
import com.tedd.dto.SecurityDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustodianMapper {



    public CustodianDto toDto(Custodian custodian) {
        if (custodian == null) {
            return null;
        }

        List<SecurityDto> securitiesDto = custodian.getSecuritiesList().stream()
                .map(SecurityMapper::toDto)
                .collect(Collectors.toList());

        return CustodianDto.builder()
                .custodianId(custodian.getCustodianID())
                .custodianName(custodian.getCustodianName())
                .country(custodian.getCountry())
                .securities(securitiesDto)
                .rating(custodian.getRating())
                .build();
    }

    public Custodian toGrpc(CustodianDto dto) {
        if (dto == null) {
            return null;
        }

        Custodian.Builder builder = Custodian.newBuilder()
                .setCustodianID(dto.getCustodianId())
                .setCustodianName(dto.getCustodianName())
                .setCountry(dto.getCountry())
                .setRating(dto.getRating());

        if (dto.getSecurities() != null) {
            dto.getSecurities().forEach(securityDto ->
                    builder.addSecurities(SecurityMapper.toGrpc(securityDto)));
        }

        return builder.build();
    }
}