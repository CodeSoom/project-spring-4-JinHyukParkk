package com.example.cotobang.errors;

public class LoginFailException extends RuntimeException {
    public LoginFailException(String email) {
        super("Login fail: " + email);
    }
}
