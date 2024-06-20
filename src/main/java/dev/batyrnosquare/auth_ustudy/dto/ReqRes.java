package dev.batyrnosquare.auth_ustudy.dto;


import jakarta.persistence.Table;
import lombok.Data;

@Data
public class ReqRes {
    private int status;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String username;
    private String email;
    private String password;
    private String role;
    private String user;

    public ReqRes(){

    }
    public ReqRes(String message){
        this.message = message;
    }
}
