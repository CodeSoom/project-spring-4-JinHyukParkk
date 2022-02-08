package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.dto.CoinRequestDto;
import com.example.cotobang.fixture.CoinFixtureFactory;
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
    ObjectMapper objectMapper;

    CoinFixtureFactory coinFactory;

    @BeforeEach
    void setUp() {
        coinFactory = new CoinFixtureFactory();

        Coin coin = coinFactory.createCoin();
        coinRepository.save(coin);
    }

    @Nested
    @DisplayName("GET /coins 요청은")
    class Describe_get_coins {

        @Test
        @DisplayName("200(Ok)와 Coin의 전체 리스트를 응답합니다.")
        void it_return_coins() throws Exception {
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

            CoinRequestDto givenCoinRequestDto;

            @BeforeEach
            void prepare() {
                givenCoinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("201(Created)와 등록된 Coin을 응답합니다.")
            void it_return_ok_and_registed_coin() throws Exception {
                mockMvc.perform(
                                post("/coins")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(coinRequestDtoToContent(givenCoinRequestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.koreanName").value(givenCoinRequestDto.getKoreanName()))
                        .andDo(print());
            }
        }

        @Nested
        @DisplayName("빈값이 포함된 coin 주어진다면")
        class Context_with_blank_coin {

            CoinRequestDto givenBlankCoinRequestDto;

            @BeforeEach
            void prepare() {
                givenBlankCoinRequestDto = coinFactory.createBlankCoinRequestDto();
            }

            @Test
            @DisplayName("400(Bad Request)를 응답합니다.")
            void it_return_bad_request() throws Exception {
                mockMvc.perform(
                                post("/coins")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(coinRequestDtoToContent(givenBlankCoinRequestDto)))
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
            CoinRequestDto coinRequestDto;

            @BeforeEach
            void prepare() {
                Coin coin = coinFactory.createCoin();
                givenId = coinRepository.save(coin).getId();

                coinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("200(Ok)와 수정된 coin을 응답합니다.")
            void it_update_coin_return_ok_and_coin() throws Exception {
                mockMvc.perform(put("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinRequestDtoToContent(coinRequestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.koreanName").value(coinRequestDto.getKoreanName()))
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
            CoinRequestDto coinRequestDto;

            @BeforeEach
            void prepare() {
                Coin coin = coinFactory.createCoin();
                givenId = coinRepository.save(coin).getId();

                coinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("201(No Content)와 삭제된 coin을 응답합니다.")
            void it_update_coin_return_ok_and_coin() throws Exception {
                mockMvc.perform(delete("/coins/" + givenId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinRequestDtoToContent(coinRequestDto)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(givenId))
                        .andExpect(jsonPath("$.koreanName").value(coinRequestDto.getKoreanName()))
                        .andDo(print());
            }
        }
    }

    private String coinRequestDtoToContent(CoinRequestDto coinRequestDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(coinRequestDto);
    }
}
