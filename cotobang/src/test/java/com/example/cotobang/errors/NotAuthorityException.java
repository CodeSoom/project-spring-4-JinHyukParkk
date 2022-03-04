package com.example.cotobang.errors;

public class NotAuthorityException extends RuntimeException {
    public NotAuthorityException(String message) {
        super("Not Authority : " + message);
    }
}
