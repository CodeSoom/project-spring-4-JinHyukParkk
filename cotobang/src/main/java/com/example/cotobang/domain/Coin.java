package com.example.cotobang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Coin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String market;

    private String koreanName;

    private String englishName;

    private String description;

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

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", market='" + market + '\'' +
                ", koreanName='" + koreanName + '\'' +
                ", englishName='" + englishName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
