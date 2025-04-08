package com.tedd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustodianDto {
    private int custodianId;
    private String custodianName;
    private String country;
    private List<SecurityDto> securities;
    private String rating;
}