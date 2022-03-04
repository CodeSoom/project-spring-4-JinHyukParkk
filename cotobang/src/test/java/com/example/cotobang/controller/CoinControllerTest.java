package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CoinDto;
import com.example.cotobang.fixture.CoinFixtureFactory;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.CoinRepository;
import com.example.cotobang.respository.UserRepository;
import com.example.cotobang.utils.JwtUtil;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CoinController 클래스")
class CoinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    UserRepository userRepository;

    UserFixtureFactory userFixtureFactory;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtUtil jwtUtil;

    CoinFixtureFactory coinFactory;


    @BeforeEach
    void setUp() {
        coinFactory = new CoinFixtureFactory();
        userFixtureFactory = new UserFixtureFactory();

        Coin coin = coinFactory.create_코인();
        coinRepository.save(coin);
    }

    @Nested
    @DisplayName("GET /coins 요청은")
    class Describe_get_coins {

        @Test
        @DisplayName("200(Ok)와 Coin의 전체 리스트를 응답합니다.")
        void it_response_200_and_coins() throws Exception {
            final int coinsSize = coinRepository.findAll().size();

            mockMvc.perform(
                            get("/coins"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(coinsSize)))
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("POST /coins 요청은")
    class Describe_post_coin {

        @Nested
        @DisplayName("coin이 주어진다면")
        class Context_with_coin {

            CoinDto givenCoinDto;
            String token;

            @BeforeEach
            void prepare() {
                givenCoinDto = coinFactory.create_코인_DTO();

                Long userId = userRepository.save(userFixtureFactory.create_사용자_Hyuk()).getId();
                token = jwtUtil.encode(userId);
            }

            @Test
            @DisplayName("201(Created)와 등록된 Coin을 응답합니다.")
            void it_response_201_and_coin() throws Exception {
                mockMvc.perform(
                                post("/coins")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(coinDtoToContent(givenCoinDto))
                                        .header("Authorization", "Bearer " + token))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.koreanName").value(givenCoinDto.getKoreanName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("coin과 유효하지 않는 token이 주어진다면")
        class Context_with_coin_and_invalidToken {

            CoinDto givenCoinDto;
            String invalidToken;

            @BeforeEach
            void prepare() {
                givenCoinDto = coinFactory.create_코인_DTO();
                invalidToken = "eyJhbGciOiJIUzI1NiJ9." +
                        "eyJ1c2VySWQiOjF9.PdEMJWhmPP4redDYU1ovusV_" +
                        "5el6JSQW5D2CGiABCDE";
            }

            @Test
            @DisplayName("401(Unauthorized)를 응답합니다.")
            void it_response_401() throws Exception {
                mockMvc.perform(
                                post("/coins")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(coinDtoToContent(givenCoinDto))
                                        .header("Authorization", "Bearer " + invalidToken))
                        .andExpect(status().isUnauthorized())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("빈값이 포함된 coin 주어진다면")
        class Context_with_blank_coin {

            CoinDto givenBlankCoinDto;

            @BeforeEach
            void prepare() {
                givenBlankCoinDto = coinFactory.create_빈값_영어이름을_갖는_코인_DTO();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_response_400() throws Exception {
                mockMvc.perform(
                                post("/coins")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(coinDtoToContent(givenBlankCoinDto)))
                        .andExpect(status().isBadRequest())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("PUT,PATCH /coins 요청은")
    class Describe_put_patch_coin {

        @Nested
        @DisplayName("id와 coin이 주어진다면")
        class Context_with_id_and_coin {

            Long givenId;
            CoinDto coinDto;
            String token;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                Coin coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();

                coinDto = coinFactory.create_코인_DTO();

                Long userId = user.getId();
                token = jwtUtil.encode(userId);
            }

            @Test
            @DisplayName("200(Ok)와 수정된 coin을 응답합니다.")
            void it_response_200_and_coin() throws Exception {
                mockMvc.perform(put("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinDtoToContent(coinDto))
                                .header("Authorization", "Bearer " + token))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.koreanName").value(coinDto.getKoreanName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("id와 coin과 유효하지 않는 token이 주어진다면")
        class Context_with_id_and_coin_and_invalid_token {

            Long givenId;
            CoinDto coinDto;
            String token;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                Coin coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();

                coinDto = coinFactory.create_코인_DTO();

                Long userId = userRepository.save(userFixtureFactory.create_사용자_Min()).getId();
                token = jwtUtil.encode(userId);
            }

            @Test
            @DisplayName("401(Unauthorized)를 응답합니다.")
            void it_response_401() throws Exception {
                mockMvc.perform(put("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinDtoToContent(coinDto))
                                .header("Authorization", "Bearer " + token))
                        .andExpect(status().isUnauthorized())
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id와 coin이 주어진다면")
        class Context_with_invalid_id_and_coin {

            Long givenId;
            CoinDto givenCoinDto;
            String token;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                Coin coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();
                coinRepository.deleteById(givenId);

                givenCoinDto = coinFactory.create_코인_DTO();

                token = jwtUtil.encode(user.getId());
            }

            @Test
            @DisplayName("404(Not Found)를 응답합니다.")
            void it_response_404() throws Exception {
                mockMvc.perform(put("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinDtoToContent(givenCoinDto))
                                .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("DELETE /coins 요청은")
    class Describe_delete_coin {

        @Nested
        @DisplayName("id가 주어진다면")
        class Context_with_id {

            Long givenId;
            Coin coin;
            String token;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();

                Long userId = user.getId();
                token = jwtUtil.encode(userId);
            }

            @Test
            @DisplayName("201(No Content)와 삭제된 coin을 응답합니다.")
            void it_response_201_and_coin() throws Exception {
                mockMvc.perform(delete("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNoContent())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.koreanName").value(coin.getKoreanName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어진다면")
        class Context_with_invalid_id {

            Long givenId;
            Coin coin;
            String token;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();
                coinRepository.deleteById(givenId);

                Long userId = user.getId();
                token = jwtUtil.encode(userId);
            }

            @Test
            @DisplayName("404(Not Found)를 응답합니다.")
            void it_response_404() throws Exception {
                mockMvc.perform(delete("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer " + token))
                        .andExpect(status().isNotFound())
                        .andDo(print());
            }
        }
    }

    private String coinDtoToContent(CoinDto coinDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(coinDto);
    }
}
