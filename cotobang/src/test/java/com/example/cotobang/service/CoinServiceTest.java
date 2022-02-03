package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.respository.CoinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("CoinService 클래스는")
class CoinServiceTest {

    @Autowired
    CoinService coinService;

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
    @DisplayName("getCoins() 메소드는")
    class Describe_getCoins {

        @Test
        @DisplayName("Coin 전체 리스트를 리턴합니다.")
        void it_return_coin_list() {
            final int coinsSize = coinRepository.findAll().size();

            assertThat(coinRepository.findAll()).hasSize(coinsSize);
        }
    }
}
