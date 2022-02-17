package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserModificationDto;
import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.errors.UserEmailDuplication;
import com.example.cotobang.errors.UserNotFoundException;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        @Nested
        @DisplayName("중복된 email의 user가 주어진다면")
        class Context_with_user_with_duplicated_email {

            UserRegistrationDto givenUserRegistrationDto;

            @BeforeEach
            void prepare() {
                userRepository.save(userFixtureFactory.create_사용자());
                givenUserRegistrationDto = userFixtureFactory.create_사용자_등록_DTO();
            }

            @Test
            @DisplayName("중복된 email을 가진 유저가 있다는 내용의 예외를 던집니다.")
            void it_throw_UserEmailDuplication() {
                assertThatThrownBy(() -> userService.createUser(givenUserRegistrationDto),
                        "유저의 email이 중복되었습니다.")
                        .isInstanceOf(UserEmailDuplicationException.class);
            }
        }
    }

    @Nested
    @DisplayName("updateUser 메소드는")
    class Describe_updateUser {

        @Nested
        @DisplayName("id와 user가 주어진다면")
        class Context_with_id_and_user {

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
        @DisplayName("유효하지 않는 id와 user가 주어진다면")
        class Context_with_invalid_id_and_user {

            Long givenInvalidId;
            UserModificationDto givenUserModificationDto;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(
                        userFixtureFactory.create_사용자()
                );
                userRepository.delete(user);

                givenInvalidId = user.getId();
                givenUserModificationDto = userFixtureFactory.create_사용자_수정_DTO();
            }

            @Test
            @DisplayName("user가 없다는 내용의 예외를 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(() -> userService.updateUser(givenInvalidId, givenUserModificationDto),
                        "업데이트할 User가 없습니다.")
                        .isInstanceOf(UserNotFoundException.class);
            }
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

        @Nested
        @DisplayName("유효하지 않는 id가 주어진다면")
        class Context_with_invalid_id {

            Long givenInvalidId;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(
                        userFixtureFactory.create_사용자()
                );
                userRepository.delete(user);

                givenInvalidId = user.getId();
            }

            @Test
            @DisplayName("user가 없다는 내용의 예외를 던집니다.")
            void it_throw_UserNotFoundException() {
                assertThatThrownBy(() -> userService.delete(givenInvalidId),
                        "삭제할 user가 없습니다.")
                        .isInstanceOf(UserNotFoundException.class);
            }
        }
    }
}
