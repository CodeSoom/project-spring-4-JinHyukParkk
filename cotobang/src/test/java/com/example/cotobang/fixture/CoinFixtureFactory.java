package com.example.cotobang.fixture;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.dto.CoinDto;

public class CoinFixtureFactory {

    public Coin createCoin() {
        return Coin.builder()
                .market("KRW-BTC")
                .koreanName("비트코인")
                .englishName("Bitcoin")
                .description("블록체인 기술을 기반으로 만들어진 온라인 암호 화폐")
                .build();
    }

    public CoinDto createCoinRequestDto() {
        return CoinDto.builder()
                .market("KRW-SOL")
                .koreanName("솔라나")
                .englishName("Solana")
                .description("퍼블릭 블록체인 플랫폼입니다.")
                .build();
    }

    public CoinDto createBlankCoinRequestDto() {
        return CoinDto.builder()
                .koreanName("위믹스")
                .build();
    }
}
