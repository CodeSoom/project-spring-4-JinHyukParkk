package com.example.cotobang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SessionRequestData {

    @Email
    private String email;

    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

    @Builder
    public SessionRequestData(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
