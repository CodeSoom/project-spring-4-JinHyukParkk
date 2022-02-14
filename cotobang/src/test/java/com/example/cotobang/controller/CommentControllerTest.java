package com.example.cotobang.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("CommentController 클래스")
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CommentController commentController;

    @Autowired
    ObjectMapper objectMapper;

    @Nested
    @DisplayName("GET /comments/{coin_id}")
    class Describe_get_comments {

        @Nested
        @DisplayName("coin id가 주어진다면")
        class Context_with_coin_id {

            Long givenCoinId;

            @BeforeEach
            void prepare() {

            }

            @Test
            void it_response_200_and_comments() throws Exception {
                mockMvc.perform(
                                get("/comments"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andDo(print());
            }
        }
    }
}
