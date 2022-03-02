package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.SessionRequestData;
import com.example.cotobang.dto.SessionResponseData;
import com.example.cotobang.fixture.SessionFixtureFactory;
import com.example.cotobang.respository.UserRepository;
import com.example.cotobang.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("AuthenticationService 클래스")
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    SessionFixtureFactory sessionFixtureFactory;

    @BeforeEach
    void setUp() {
        sessionFixtureFactory = new SessionFixtureFactory();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class login_메소드는 {

        SessionRequestData givenSessionRequestData;

        @BeforeEach
        void prepare() {
            givenSessionRequestData = sessionFixtureFactory.세션_요청_데이터();

            User user = User.builder()
                    .email(givenSessionRequestData.getEmail())
                    .password(givenSessionRequestData.getPassword())
                    .build();

            userRepository.save(user);
        }

        @Test
        void access_token을_반환합니다() {
            String accessToken = authenticationService.login(givenSessionRequestData);

            assertThat(accessToken).contains(".");
        }

    }


}
