package com.example.cotobang.controller;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.UserModificationDto;
import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserController 클래스는")
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    UserFixtureFactory userFixtureFactory;

    @BeforeEach
    void setUp() {
        userFixtureFactory = new UserFixtureFactory();
    }

    @Nested
    @DisplayName("POST /users 요청은")
    class Describe_post_user {

        @Nested
        @DisplayName("user가 주어진다면")
        class Context_with_user {

            UserRegistrationDto givenUserRegistrationDto;

            @BeforeEach
            void prepare() {
                givenUserRegistrationDto = userFixtureFactory.create_사용자_등록_DTO();
            }

            @Test
            @DisplayName("201(Created)와 등록된 user를 응답합니다.")
            void it_response_201_and_user() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userRegistrationDtoToContent(givenUserRegistrationDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value(givenUserRegistrationDto.getName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("email 형식이 잘못된 user가 주어진다면")
        class Context_with_user_with_invalid_email {

            UserRegistrationDto givenInvalidUserRegistrationDto;

            @BeforeEach
            void prepare() {
                givenInvalidUserRegistrationDto = userFixtureFactory.create_잘못된_이메일을_갖는_사용자_DTO();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_response_400() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userRegistrationDtoToContent(givenInvalidUserRegistrationDto)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("email 이 빈값인 user가 주어진다면")
        class Context_with_user_with_blank_email {

            UserRegistrationDto givenInvalidUserRegistrationDto;

            @BeforeEach
            void prepare() {
                givenInvalidUserRegistrationDto = userFixtureFactory.create_빈값_이메일을_갖는_사용자_DTO();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_response_400() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userRegistrationDtoToContent(givenInvalidUserRegistrationDto)))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("중복된 email을 가진 user가 주어진다면")
        class Context_with_user_with_duplicated_email {

            UserRegistrationDto givenUserRegistrationDto;

            @BeforeEach
            void prepare() {
                userRepository.save(userFixtureFactory.create_중복테스트용_사용자());

                givenUserRegistrationDto = userFixtureFactory.create_중복테스트용_사용자_등록_DTO();
            }

            @Test
            @DisplayName("404(Not Found)를 응답합니다.")
            void it_response_404() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userRegistrationDtoToContent(givenUserRegistrationDto)))
                        .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("PUT,PATCH /users/{id} 요청은")
    class Describe_put_patch_user {

        @Nested
        @DisplayName("id와 user 정보가 주어진다면")
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
            @DisplayName("200(Ok)와 수정된 user를 응답합니다.")
            void it_response_200_and_user() throws Exception {
                mockMvc.perform(put("/users/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userModificationDtoDtoToContent(givenUserModificationDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.name").value(givenUserModificationDto.getName()))
                        .andDo(print());
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
            @DisplayName("404(Not Found)를 응답합니다.")
            void it_response_404() throws Exception {
                mockMvc.perform(put("/users/" + givenInvalidId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(userModificationDtoDtoToContent(givenUserModificationDto)))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /users/{id} 요청은")
    class Describe_detele_user {

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
            @DisplayName("204(No content)와 삭제된 user를 응답합니다.")
            void it_response_204_and_user() throws Exception {
                mockMvc.perform(delete("/users/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andDo(print());
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
            @DisplayName("404(Not Found)를 응답합니다.")
            void it_throw_UserNotFoundException() throws Exception {
                mockMvc.perform(delete("/users/" + givenInvalidId)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }

    private String userRegistrationDtoToContent(UserRegistrationDto userRegistrationDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userRegistrationDto);
    }

    private String userModificationDtoDtoToContent(UserModificationDto userModificationDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userModificationDto);
    }
}
