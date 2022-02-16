package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserModificationDto;
import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.fixture.UserFixtureFactory;
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

    UserFixtureFactory userFixtureFactory;

    @BeforeEach
    void setUp() {
        userFixtureFactory = new UserFixtureFactory();
    }

    @Nested
    @DisplayName("createUser 메소드는")
    class Describe_createUser {

        @Nested
        @DisplayName("user가 주어진다면")
        class Context_with_user {

            UserRegistrationDto givenUserRegistrationDto;

            @BeforeEach
            void prepare() {
                givenUserRegistrationDto = userFixtureFactory.create_사용자_등록_DTO();
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

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUser {

        Long givenId;
        UserModificationDto givenUserModificationDto;

        @BeforeEach
        void prepare() {
            User user = userFixtureFactory.create_사용자();
            givenId = userRepository.save(user)
                    .getId();

            givenUserModificationDto = userFixtureFactory.create_사용자_수정_DTO();
        }

        @Test
        @DisplayName("user를 업데이트하고 리턴합니다.")
        void it_update_user_return_user() {
            User user = userService.updateUser(givenId, givenUserModificationDto);

            assertThat(user).isNotNull();
            assertThat(user.getName()).isEqualTo(givenUserModificationDto.getName());
        }
    }

    @Nested
    @DisplayName("deleteUser 메소드는")
    class Describe_deleteUser {

        @Nested
        @DisplayName("id가 주어진다면")
        class Context_with_id {

            Long givenId;

            @BeforeEach
            void prepare() {
                User user = userFixtureFactory.create_사용자();
                givenId = userRepository.save(user)
                        .getId();
            }

            @Test
            @DisplayName("user를 삭제하고 리턴합니다.")
            void it_delete_user_return_user() {
                User user = userService.delete(givenId);

                assertThat(user).isNotNull();
            }
        }
    }
}
