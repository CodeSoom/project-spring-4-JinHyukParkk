package com.example.cotobang.fixture;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.dto.CoinRequestDto;

public class CoinFixtureFactory {

    public Coin createCoin() {
        return Coin.builder()
                .market("KRW-BTC")
                .koreanName("비트코인")
                .englishName("Bitcoin")
                .description("블록체인 기술을 기반으로 만들어진 온라인 암호 화폐")
                .build();
    }

    public CoinRequestDto createCoinRequestDto() {
        return CoinRequestDto.builder()
                .market("KRW-SOL")
                .koreanName("솔라나")
                .englishName("Solana")
                .build();
    }

    public CoinRequestDto createBlankCoinRequestDto() {
        return CoinRequestDto.builder()
                .koreanName("솔라나")
                .build();
    }
}
