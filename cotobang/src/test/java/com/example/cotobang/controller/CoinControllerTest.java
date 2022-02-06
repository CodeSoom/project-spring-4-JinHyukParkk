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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("POST /coin 요청은")
    class Describe_post_coin {

        @Nested
        @DisplayName("등록된 coin이 주어진다면")
        class Context_with_registered_coin {

            CoinRequestDto givenCoinRequestDto;

            @BeforeEach
            void prepare() {
                givenCoinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("201(Created)와 등록된 Coin을 응답합니다.")
            void it_return_registed_coin() throws Exception {
                mockMvc.perform(
                        post("/coin")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(coinRequestDtoToContent(givenCoinRequestDto)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.korean_name").value(givenCoinRequestDto.getKoreanName()))
                        .andDo(print());
            }
        }
    }

    }

    private String coinRequestDtoToContent(CoinRequestDto coinRequestDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(coinRequestDto);
    }

}
