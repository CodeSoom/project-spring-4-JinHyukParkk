package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.respository.CoinRepository;
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

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("CoinController 클래스")
class CoinControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoinRepository coinRepository;

    @BeforeEach
    void setUp() {
        Coin coin = Coin.builder()
                .koreanName("비트코인")
                .build();

        coinRepository.save(coin);
    }

    @Nested
    @DisplayName("GET /coins 요청은")
    class Describe_get_coins {

        @Test
        @DisplayName("200(Ok)와 Coin의 전체 리스트를 응답합니다.")
        void it_return_coins() throws Exception {
            final int coinsSize = coinRepository.findAll().size();

            mockMvc.perform(get("/coins"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(coinsSize)))
                    .andDo(print());
        }
    }
}
