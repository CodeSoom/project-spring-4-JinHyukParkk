package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CoinDto;
import com.example.cotobang.errors.CoinNotFoundException;
import com.example.cotobang.errors.NotAuthorityException;
import com.example.cotobang.respository.CoinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 코인에 대한 로직 처리
 */
@Service
@Transactional
public class CoinService {

    private final CoinRepository coinRepository;

    public CoinService(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    /**
     * 등록된 전체 코인 리스트를 반환합니다.
     *
     * @return 전체 코인 리스트
     */
    public List<Coin> getCoins() {
        return coinRepository.findAll();
    }

    public Coin getCoin(Long id) {
        return coinRepository.findById(id)
                .orElseThrow(() -> new CoinNotFoundException(id));
    }

    public Coin createCoin(CoinDto coinDto, User user) {
        final Coin coin = Coin.builder()
                .market(coinDto.getMarket())
                .koreanName(coinDto.getKoreanName())
                .englishName(coinDto.getEnglishName())
                .description(coinDto.getDescription())
                .user(user)
                .build();

        return coinRepository.save(coin);
    }

    public Coin updateCoin(Long targetId, CoinDto source, User user) {
        Coin coin = getCoin(targetId);

        if (!user.compare(coin.getUser())) {
            throw new NotAuthorityException(user.getName());
        }

        coin.change(
                source.getMarket(),
                source.getKoreanName(),
                source.getEnglishName(),
                source.getDescription()
        );

        return coin;
    }

    public Coin deleteCoin(Long id, User user) {
        Coin coin = getCoin(id);

        if (!user.compare(coin.getUser())) {
            throw new NotAuthorityException(user.getName());
        }

        coinRepository.delete(coin);

        return coin;
    }
}
