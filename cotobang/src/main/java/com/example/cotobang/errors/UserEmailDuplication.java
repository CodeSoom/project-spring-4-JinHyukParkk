package com.example.cotobang.errors;

public class UserEmailDuplication extends RuntimeException{

    public UserEmailDuplication(String email) {
        super("User email is already existed : " + email);
    }
}
