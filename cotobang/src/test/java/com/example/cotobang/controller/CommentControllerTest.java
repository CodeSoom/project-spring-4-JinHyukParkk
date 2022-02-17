package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.fixture.CoinFixtureFactory;
import com.example.cotobang.fixture.CommentFixtureFactory;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.CoinRepository;
import com.example.cotobang.respository.CommentRepository;
import com.example.cotobang.respository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DisplayName("CommentController 클래스")
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CommentController commentController;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    CoinFixtureFactory coinFixtureFactory;

    UserFixtureFactory userFixtureFactory;

    CommentFixtureFactory commentFixtureFactory;

    @BeforeEach
    void setUp() {
        coinFixtureFactory = new CoinFixtureFactory();
        userFixtureFactory = new UserFixtureFactory();
        commentFixtureFactory = new CommentFixtureFactory();
    }

    @Nested
    @DisplayName("GET /comments/{coin_id}")
    class Describe_get_comments {

        @Nested
        @DisplayName("coin id가 주어진다면")
        class Context_with_coin_id {

            Long givenCoidId;

            @BeforeEach
            void prepare() {
                Coin coin = coinRepository.save(coinFixtureFactory.create_코인());
                User user = userRepository.save(userFixtureFactory.create_사용자());

                Comment comment = commentFixtureFactory.create_댓글(coin, user);
                commentRepository.save(comment);

                givenCoidId = coin.getId();
            }

            @Test
            void it_response_200_and_comments() throws Exception {
                mockMvc.perform(get("/comments/" + givenCoidId))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andDo(print());
            }
        }
    }
}
