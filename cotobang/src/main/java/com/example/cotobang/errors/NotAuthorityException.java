package com.example.cotobang.errors;

public class NotAuthorityException extends RuntimeException {
    public NotAuthorityException(String name) {
        super("Not Authority : " + name);
    }
}
