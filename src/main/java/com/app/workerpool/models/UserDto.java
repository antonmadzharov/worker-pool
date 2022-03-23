package com.app.workerpool.models;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
}