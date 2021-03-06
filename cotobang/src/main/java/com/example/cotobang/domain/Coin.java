package com.example.cotobang.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Coin(String market,
                String koreanName,
                String englishName,
                String description,
                User user) {
        this.market = market;
        this.koreanName = koreanName;
        this.englishName = englishName;
        this.description = description;
        this.user = user;
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

    public boolean compare(Coin coin) {
        return id == coin.getId();
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
