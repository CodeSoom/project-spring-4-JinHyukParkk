package com.example.cotobang.fixture;

import com.example.cotobang.dto.SessionRequestData;

public class SessionFixtureFactory {

    public SessionRequestData 세션_요청_데이터() {
        return SessionRequestData.builder()
                .email("pjh0819@test.com")
                .password("test123")
                .build();
    }
}
