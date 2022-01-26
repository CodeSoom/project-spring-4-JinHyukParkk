package com.example.cotobang.client;

import com.example.cotobang.dto.CoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("UpbitClient 클래스")
@SpringBootTest
class UpbitClientTest {

    @Autowired
    private UpbitClient upbitClient;

    @Nested
    @DisplayName("list() 메소드는")
    class Describe_list {

        @Test
        @DisplayName("코인 리스트를 가져옵니다.")
        void it_return_coin_list() {
            List<CoinResponse> coinResponses = upbitClient.list();

            assertThat(coinResponses).hasSizeGreaterThan(0);
            assertThat(coinResponses.get(0)).isNotNull();
        }
    }
}
