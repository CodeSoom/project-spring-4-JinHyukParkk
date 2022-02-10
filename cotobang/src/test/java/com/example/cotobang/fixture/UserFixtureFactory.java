package com.example.cotobang.fixture;

import com.example.cotobang.dto.UserRegistrationDto;

public class UserFixtureFactory {

    public UserRegistrationDto createUserRegistrationDto() {
        return UserRegistrationDto.builder()
                .email("pjh08190819@naver.com")
                .name("hyuk")
                .password("1234")
                .build();
    }
}
