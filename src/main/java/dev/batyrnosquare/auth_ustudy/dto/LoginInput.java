package dev.batyrnosquare.auth_ustudy.dto;


import lombok.Data;

@Data
public class LoginInput {
    private String email;
    private String password;
}
