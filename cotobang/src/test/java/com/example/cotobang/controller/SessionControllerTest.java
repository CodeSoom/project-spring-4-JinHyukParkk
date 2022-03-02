package com.example.cotobang.controller;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.SessionRequestData;
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

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("SessionController 클래스")
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    SessionRequestData sessionRequestData;

    @BeforeEach
    void setUp() {
        sessionRequestData = SessionRequestData.builder()
                .email("pjh08190819@codesom.com")
                .password("123456")
                .build();
    }

    @Nested
    @DisplayName("POST /session 요청은")
    class Describe_post_session {

        @Nested
        @DisplayName("SessionRequestData가 주어진다면")
        class Context_with_sessionRequestData {

            @BeforeEach
            void prepare() {
                User user = User.builder()
                        .email(sessionRequestData.getEmail())
                        .password(sessionRequestData.getPassword())
                        .build();

                userRepository.save(user);
            }

            @Test
            @DisplayName("access token을 응답합니다.")
            void it_return_accessToken() throws Exception {
                mockMvc.perform(post("/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sessionRequestDataToContent(sessionRequestData)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.accessToken").value(containsString(".")))
                        .andDo(print());
            }
        }
    }

    private String sessionRequestDataToContent(SessionRequestData sessionRequestData) throws JsonProcessingException {
        return objectMapper.writeValueAsString(sessionRequestData);
    }
}
