package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CoinDto;
import com.example.cotobang.errors.CoinNotFoundException;
import com.example.cotobang.errors.NotAuthorityException;
import com.example.cotobang.fixture.CoinFixtureFactory;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.CoinRepository;
import com.example.cotobang.respository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DisplayName("CoinService 클래스는")
class CoinServiceTest {

    @Autowired
    CoinService coinService;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    UserRepository userRepository;

    CoinFixtureFactory coinFactory;

    UserFixtureFactory userFixtureFactory;

    @BeforeEach
    void setUp() {
        coinFactory = new CoinFixtureFactory();
        userFixtureFactory = new UserFixtureFactory();

        Coin coin = coinFactory.create_코인();
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

            CoinDto givenCoinDto;
            User user;

            @BeforeEach
            void prepare() {
                givenCoinDto = coinFactory.create_코인_DTO();
                user = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(user);
            }

            @Test
            @DisplayName("coin을 생성하고 리턴합니다")
            void it_created_coin_return_coin() {
                Coin coin = coinService.createCoin(givenCoinDto, user);

                assertThat(coin.getKoreanName()).isEqualTo(givenCoinDto.getKoreanName());
            }
        }
    }

    @Nested
    @DisplayName("updateCoin 메소드는")
    class Describe_updateCoin {

        @Nested
        @DisplayName("id와 coin과 user가 주어진다면")
        class Context_with_id_and_coin_and_user {

            Long givenId;
            CoinDto givenCoinDto;
            User givenUser;

            @BeforeEach
            void prepare() {
                givenUser = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(givenUser);

                Coin coin = coinFactory.create_코인(givenUser);
                givenId = coinRepository.save(coin)
                        .getId();

                givenCoinDto = coinFactory.create_코인_DTO();

            }

            @Test
            @DisplayName("주어진 id의 coin을 수정하고 리턴합니다.")
            void it_update_coin_return_coin() {
                Coin coin = coinService.updateCoin(givenId, givenCoinDto, givenUser);

                assertThat(coin.getKoreanName()).isEqualTo(givenCoinDto.getKoreanName());
            }
        }

        @Nested
        @DisplayName("id와 coin과 권한 없는 user가 주어진다면")
        class Context_with_id_and_coin_and_invalid_user {

            Long givenId;
            CoinDto givenCoinDto;
            User givneUser;

            @BeforeEach
            void prepare() {
                User user = userRepository.save(userFixtureFactory.create_사용자_Hyuk());

                Coin coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin)
                        .getId();

                givenCoinDto = coinFactory.create_코인_DTO();

                givneUser = userFixtureFactory.create_사용자_Min();
                userRepository.save(givneUser);
            }

            @Test
            @DisplayName("권한이 없다는 내용의 예외를 던집니다.")
            void it_return_notAuthorityException() {
                assertThatThrownBy(() -> coinService.updateCoin(givenId, givenCoinDto, givneUser))
                        .isInstanceOf(NotAuthorityException.class);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id와 coin과 user가 주어진다면")
        class Context_with_invalid_id_and_coin_and_user {

            Long givenId;
            CoinDto givenCoinDto;
            User givenUser;

            @BeforeEach
            void prepare() {
                givenUser = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(givenUser);

                Coin coin = coinFactory.create_코인(givenUser);
                givenId = coinRepository.save(coin).getId();
                coinRepository.deleteById(givenId);

                givenCoinDto = coinFactory.create_코인_DTO();
            }

            @Test
            @DisplayName("coin이 없다는 내용의 에외를 던집니다.")
            void it_update_coin_return_coin() {
                assertThatThrownBy(() -> coinService.updateCoin(givenId, givenCoinDto, givenUser))
                        .isInstanceOf(CoinNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteCoin 메소드는")
    class Describe_deleteCoin {

        @Nested
        @DisplayName("id와 user가 주어진다면")
        class Context_with_id_and_user {

            Long givenId;
            User givenUser;

            @BeforeEach
            void prepare() {
                givenUser = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(givenUser);

                Coin coin = coinFactory.create_코인(givenUser);
                givenId = coinRepository.save(coin).getId();
            }

            @Test
            @DisplayName("주어진 id의 coin을 삭제하고 리턴합니다.")
            void it_update_coin_return_coin() {
                coinService.deleteCoin(givenId, givenUser);

                Coin foundCoin = coinRepository.findById(givenId).orElse(null);
                assertThat(foundCoin).isNull();
            }
        }

        @Nested
        @DisplayName("id와 권한 없는 user가 주어진다면")
        class Context_with_id_and_invalid_user {

            Long givenId;
            User givenUser;

            @BeforeEach
            void prepare() {
                User user = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(user);

                Coin coin = coinFactory.create_코인(user);
                givenId = coinRepository.save(coin).getId();

                givenUser = userFixtureFactory.create_사용자_Min();
                userRepository.save(givenUser);
            }

            @Test
            @DisplayName("권한이 없다는 내용의 예외를 던집니다.")
            void it_return_notAuthorityException() {
                assertThatThrownBy(() -> coinService.deleteCoin(givenId, givenUser))
                        .isInstanceOf(NotAuthorityException.class);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 id가 주어진다면")
        class Context_with_invalid_id_and_user {

            Long givenId;
            User givenUser;

            @BeforeEach
            void prepare() {
                givenUser = userFixtureFactory.create_사용자_Hyuk();
                userRepository.save(givenUser);

                Coin coin = coinFactory.create_코인(givenUser);
                givenId = coinRepository.save(coin).getId();

                coinRepository.deleteById(givenId);
            }

            @Test
            @DisplayName("coin이 없다는 내용의 에외를 던집니다.")
            void it_update_coin_return_coin() {
                assertThatThrownBy(() -> coinService.deleteCoin(givenId, givenUser))
                        .isInstanceOf(CoinNotFoundException.class);
            }
        }
    }
}
