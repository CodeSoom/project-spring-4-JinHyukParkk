package com.example.cotobang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoinRequestDto {

    private String market;

    private String koreanName;

    private String englishName;

    private String description;

    @Builder
    public CoinRequestDto(String market,
                          String koreanName,
                          String englishName,
                          String description) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.description = description;
    }
}
