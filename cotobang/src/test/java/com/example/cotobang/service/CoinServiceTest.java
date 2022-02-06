package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.dto.CoinRequestDto;
import com.example.cotobang.fixture.CoinFixtureFactory;
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

    CoinFixtureFactory coinFactory;

    @BeforeEach
    void setUp() {
        coinFactory = new CoinFixtureFactory();

        Coin coin = coinFactory.createCoin();
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

    @Nested
    @DisplayName("createCoin 메소드는")
    class Describe_createCoin {

        @Nested
        @DisplayName("등록할 coin이 주어진다면")
        class Context_with_coin {

            CoinRequestDto givenCoinRequestDto;

            @BeforeEach
            void prepare() {
                givenCoinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("coin을 생성하고 리턴합니다")
            void it_created_coin_return_coin() {
                Coin coin = coinService.createCoin(givenCoinRequestDto);

                assertThat(coin.getKoreanName()).isEqualTo(givenCoinRequestDto.getKoreanName());
            }
        }
    }

    @Nested
    @DisplayName("updateCoin 메소드는")
    class Describe_updateCoin {

        @Nested
        @DisplayName("id와 coin이 주어진다면")
        class Context_with_id_and_coin {

            Long givenId;
            CoinRequestDto givenCoinRequestDto;

            @BeforeEach
            void prepare() {
                Coin coin = coinFactory.createCoin();
                givenId = coinRepository.save(coin).getId();

                givenCoinRequestDto = coinFactory.createCoinRequestDto();
            }

            @Test
            @DisplayName("주어진 id의 coin을 수정하고 리턴합니다.")
            void it_update_coin_return_coin() {
                Coin coin = coinService.updateCoin(givenId, givenCoinRequestDto);

                assertThat(coin.getKoreanName()).isEqualTo(givenCoinRequestDto.getKoreanName());
            }
        }
    }
}
