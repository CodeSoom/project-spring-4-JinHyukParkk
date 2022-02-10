package com.example.cotobang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegistrationDto {

    private String email;

    private String name;

    private String password;

    @Builder
    public UserRegistrationDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
