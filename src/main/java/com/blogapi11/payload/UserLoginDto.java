package com.blogapi11.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserLoginDto {

    @Column(nullable = false)
    private String usernameOrEmail;

    @Column(nullable = false)
    private String password;
}
