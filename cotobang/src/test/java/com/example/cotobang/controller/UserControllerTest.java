package com.example.cotobang.controller;

import com.example.cotobang.dto.UserRegistrationDto;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.CoinRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    CoinRepository coinRepository;

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
                givenUserRegistrationDto = userFixtureFactory.createUserRegistrationDto();
            }

            @Test
            @DisplayName("201(Created)와 등록된 user를 응답합니다.")
            void it_return_ok_and_registed_user() throws Exception {
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
                givenInvalidUserRegistrationDto = userFixtureFactory.createUserRegistrationDtoWithInvalidEmail();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_ok_and_registed_user() throws Exception {
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
                givenInvalidUserRegistrationDto = userFixtureFactory.createUserRegistrationDtoWithBlankEmail();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_ok_and_registed_user() throws Exception {
                mockMvc.perform(
                                post("/users")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(userRegistrationDtoToContent(givenInvalidUserRegistrationDto)))
                        .andExpect(status().isBadRequest());
            }
        }
    }

    private String userRegistrationDtoToContent(UserRegistrationDto userRegistrationDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(userRegistrationDto);
    }
}
