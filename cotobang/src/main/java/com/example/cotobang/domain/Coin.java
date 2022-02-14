package com.example.cotobang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "coin_id")
    private List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    public Coin(String market,
                String koreanName,
                String englishName,
                String description) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.description = description;
    }

    public void change(String market,
                String koreanName,
                String englishName,
                String description) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.description = description;
    }
}
