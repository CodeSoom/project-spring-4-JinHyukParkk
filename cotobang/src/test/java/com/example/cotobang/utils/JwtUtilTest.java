package com.example.cotobang.utils;

import com.example.cotobang.errors.InvalidAccessTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("JwtUtil 클래스")
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;

    private static final Long USER_ID = 1L;
    private static final String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.PdEMJWhmPP4redDYU1ovusV_" +
            "5el6JSQW5D2CGiWqUOE";
    private static final String INVALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9." +
            "eyJ1c2VySWQiOjF9.PdEMJWhmPP4redDYU1ovusV_" +
            "5el6JSQW5D2CGiABCDE";

    @Nested
    @DisplayName("encode() 메소드는")
    class Describe_encode {

        @Test
        @DisplayName("token을 리턴합니다.")
        void it_return_token() {
            String token = jwtUtil.encode(USER_ID);

            assertThat(token).isEqualTo(VALID_TOKEN);
        }
    }

    @Nested
    @DisplayName("decode() 메소드는")
    class Describe_decode {

        @Nested
        @DisplayName("유효한 Token이 주어진다면")
        class Context_with_token {

            @Test
            @DisplayName("userId 정보를 리턴합니다.")
            void it_return_userId() {
                Claims claims = jwtUtil.decode(VALID_TOKEN);

                assertThat(claims.get("userId", Long.class)).isEqualTo(USER_ID);
            }
        }

        @Nested
        @DisplayName("유효하지 않는 Token이 주어진다면")
        class Context_with_invalid_token {

            @Test
            @DisplayName("InvalidAccessTokenException 던집니다.")
            void it_return_InvalidAccessTokenException() {
                assertThatThrownBy(() -> jwtUtil.decode(INVALID_TOKEN))
                        .isInstanceOf(InvalidAccessTokenException.class);
            }
        }
    }
}
