package com.example.cotobang.domain;

import com.example.cotobang.fixture.UserFixtureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User 클래스")
class UserTest {

    UserFixtureFactory userFixtureFactory;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userFixtureFactory = new UserFixtureFactory();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class change_메서드는 {

        User givenUser;
        User givenChangeUser;

        @BeforeEach
        void prepare() {
            givenUser = userFixtureFactory.create_사용자_Hyuk();

            givenChangeUser = userFixtureFactory.create_사용자_Min();

            givenUser.change(
                    givenChangeUser.getName()
            );
        }

        @Test
        void 유저_정보를_변경합니다() {
            assertThat(givenChangeUser.getName())
                    .isEqualTo(givenChangeUser.getName());

        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class authenticate_메서드는 {

        User givenUser;
        String givenUpdatePassword  = "test";

        @BeforeEach
        void prepare() {
            givenUser = userFixtureFactory.create_사용자_Hyuk();

            givenUser.changePassword(
                    givenUpdatePassword,
                    passwordEncoder
            );
        }

        @Test
        void 유저_정보를_변경합니다() {
            assertThat(givenUser.authenticate(givenUpdatePassword, passwordEncoder))
                    .isEqualTo(true);

        }
    }
}
