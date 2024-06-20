package dev.batyrnosquare.auth_ustudy.dto;


import lombok.Data;

@Data
public class RegisterInput {
    private String f_name;
    private String l_name;
    private String email;
    private String password;
    private String role;
}
