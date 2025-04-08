package com.tedd.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorResponse {
    private Integer authorID;
    private String firstName;
    private String lastName;
    private String gender;
}
