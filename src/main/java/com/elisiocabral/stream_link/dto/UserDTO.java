package com.elisiocabral.stream_link.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public UserDTO() {}


}
