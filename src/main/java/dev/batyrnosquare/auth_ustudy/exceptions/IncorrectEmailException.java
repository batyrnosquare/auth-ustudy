package dev.batyrnosquare.auth_ustudy.exceptions;

public class IncorrectEmailException extends RuntimeException{
    public IncorrectEmailException(String message) {
        super(message);
    }
}
