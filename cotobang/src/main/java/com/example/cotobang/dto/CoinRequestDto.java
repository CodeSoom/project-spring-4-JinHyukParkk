package com.example.cotobang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CoinRequestDto {

    @NotBlank
    private String market;

    @NotBlank
    private String koreanName;

    @NotBlank
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
