package com.tedd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityDto {
    private int securityId;
    private int custodianId;
    private String securityName;
    private SecurityTypeDto type;
    private float price;
    private int quantity;
    private String symbol;
}