package com.tedd.dto;

public enum SecurityTypeDto {
    STOCK,
    BOND,
    ETF;

    // Convert from gRPC enum to DTO enum
    public static SecurityTypeDto fromGrpc(com.tedd.SecurityType grpcType) {
        return switch (grpcType) {
            case STOCK -> STOCK;
            case BOND -> BOND;
            case ETF -> ETF;
            default -> throw new IllegalArgumentException("Unknown security type: " + grpcType);
        };
    }

    // Convert from DTO enum to gRPC enum
    public com.tedd.SecurityType toGrpc() {
        return switch (this) {
            case STOCK -> com.tedd.SecurityType.STOCK;
            case BOND -> com.tedd.SecurityType.BOND;
            case ETF -> com.tedd.SecurityType.ETF;
            default -> throw new IllegalArgumentException("Unknown security type: " + this);
        };
    }
}