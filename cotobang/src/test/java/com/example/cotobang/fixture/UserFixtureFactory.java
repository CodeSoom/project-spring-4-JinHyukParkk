package com.example.cotobang.fixture;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserModificationDto;
import com.example.cotobang.dto.UserRegistrationDto;

public class UserFixtureFactory {

    public User create_사용자() {
        return User.builder()
                .email("pjh08190819@naver.com")
                .name("hyuk")
                .password("1234")
                .build();
    }

    public UserRegistrationDto create_사용자_등록_DTO() {
        return UserRegistrationDto.builder()
                .email("pjh08190819@naver.com")
                .name("hyuk")
                .password("1234")
                .build();
    }

    public UserRegistrationDto create_잘못된_이메일을_갖는_사용자_DTO() {
        return UserRegistrationDto.builder()
                .email("pjh08190819")
                .name("hyuk")
                .password("1234")
                .build();
    }

    public UserRegistrationDto create_빈값_이메일을_갖는_사용자_DTO() {
        return UserRegistrationDto.builder()
                .name("hyuk")
                .password("1234")
                .build();
    }

    public UserModificationDto create_사용자_수정_DTO() {
        return UserModificationDto.builder()
                .name("hyuk_update")
                .password("1234###")
                .build();
    }
}
