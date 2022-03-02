package com.example.cotobang.fixture;

import com.example.cotobang.dto.SessionRequestData;

public class SessionFixtureFactory {

    public SessionRequestData a_유저_세션_요청_데이터() {
        return SessionRequestData.builder()
                .email("auser@test.com")
                .password("test123")
                .build();
    }

    public SessionRequestData b_유저_세션_요청_데이터() {
        return SessionRequestData.builder()
                .email("buser@test.com")
                .password("test123")
                .build();
    }

    public SessionRequestData c_유저_세션_요청_데이터() {
        return SessionRequestData.builder()
                .email("cuser@test.com")
                .password("test123")
                .build();
    }
}
