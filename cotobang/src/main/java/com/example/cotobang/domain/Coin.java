package com.example.cotobang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private String koreanName;

    private String englishName;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Coin(String market,
                String koreanName,
                String englishName,
                String description,
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
