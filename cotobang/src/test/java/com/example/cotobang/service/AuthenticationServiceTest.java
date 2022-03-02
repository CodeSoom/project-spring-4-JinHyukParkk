package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.SessionRequestData;
import com.example.cotobang.errors.LoginFailException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록_유저_세션이_주어진다면 {

            SessionRequestData givenSessionRequestData;

            @BeforeEach
            void prepare() {
                givenSessionRequestData = sessionFixtureFactory.a_유저_세션_요청_데이터();

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

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록되지_않은_유저_세션이_주어진다면 {

            SessionRequestData givenSessionRequestData;

            @BeforeEach
            void prepare() {
                givenSessionRequestData = sessionFixtureFactory.b_유저_세션_요청_데이터();
            }

            @Test
            void 로그인에_실패했다는_내용의_예외를_던집니다() {
                assertThatThrownBy(() -> authenticationService.login(givenSessionRequestData))
                        .isInstanceOf(LoginFailException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 비밀번호가_다른_유저_세션이_주어진다면 {

            SessionRequestData givenSessionRequestData;

            @BeforeEach
            void prepare() {
                givenSessionRequestData = sessionFixtureFactory.a_유저_세션_요청_데이터();

                User user = User.builder()
                        .email(givenSessionRequestData.getEmail())
                        .password("invalid")
                        .build();

                userRepository.save(user);
            }

            @Test
            void 로그인에_실패했다는_내용의_예외를_던집니다() {
                assertThatThrownBy(() -> authenticationService.login(givenSessionRequestData))
                        .isInstanceOf(LoginFailException.class);
            }
        }
    }
}
