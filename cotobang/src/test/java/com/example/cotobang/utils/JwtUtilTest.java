package com.example.cotobang.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("JwtUtil 클래스")
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Nested
    @DisplayName("encode() 메소드는")
    class Describe_encode {

        Long givenUserId = 1L;
        String givenValidToken = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJ1c2VySWQiOjF9.PdEMJWhmPP4redDYU1ovusV_" +
                "5el6JSQW5D2CGiWqUOE";

        @Test
        @DisplayName("token을 리턴합니다.")
        void it_return_token() {
            String token = jwtUtil.encode(givenUserId);

            assertThat(token).isEqualTo(givenValidToken);
        }
    }
}
