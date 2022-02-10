package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("UserService 클래스는")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {

        @Nested
        @DisplayName("user가 주어진다면")
        class Context_with_user{

            UserRegistrationDto givenUserRegistrationDto;

            @BeforeEach
            void prepare() {
                givenUserRegistrationDto = UserRegistrationDto.builder()
                        .email("pjh08190819@naver.com")
                        .name("hyuk")
                        .password("1234")
                        .build();
            }

            @Test
            @DisplayName("user를 생성하고 리턴합니다.")
            void it_create_user_return_user() {
                User user = userService.createUser(givenUserRegistrationDto);

                assertThat(user).isNotNull();
                assertThat(user.getEmail()).isEqualTo(givenUserRegistrationDto.getEmail());

            }

        }
    }
}
