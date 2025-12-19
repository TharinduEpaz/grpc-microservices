package com.tedd.dto;

public enum BookGenreDto {
    FICTION,
    NON_FICTION,
    SCI_FI,
    MYSTERY,
    ROMANCE,
    FANTASY;

    // Convert from gRPC enum to DTO enum
    public static BookGenreDto fromGrpc(com.tedd.BookGenre grpcGenre) {
        return switch (grpcGenre) {
            case FICTION -> FICTION;
            case NON_FICTION -> NON_FICTION;
            case SCI_FI -> SCI_FI;
            case MYSTERY -> MYSTERY;
            case ROMANCE -> ROMANCE;
            case FANTASY -> FANTASY;
            default -> throw new IllegalArgumentException("Unknown book genre: " + grpcGenre);
        };
    }

    // Convert from DTO enum to gRPC enum
    public com.tedd.BookGenre toGrpc() {
        return switch (this) {
            case FICTION -> com.tedd.BookGenre.FICTION;
            case NON_FICTION -> com.tedd.BookGenre.NON_FICTION;
            case SCI_FI -> com.tedd.BookGenre.SCI_FI;
            case MYSTERY -> com.tedd.BookGenre.MYSTERY;
            case ROMANCE -> com.tedd.BookGenre.ROMANCE;
            case FANTASY -> com.tedd.BookGenre.FANTASY;
            default -> throw new IllegalArgumentException("Unknown book genre: " + this);
        };
    }
}

