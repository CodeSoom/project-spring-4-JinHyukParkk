package com.example.cotobang.errors;

public class InvalidAccessTokenException extends RuntimeException{
    public InvalidAccessTokenException(String accessToken) {
        super("Invalid access token : " + accessToken);
    }
}
