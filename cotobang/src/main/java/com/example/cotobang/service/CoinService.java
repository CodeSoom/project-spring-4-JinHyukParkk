package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.respository.CoinRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 코인에 대한 로직 처리
 *
 * @author Hyuk (pjh08190819@naver.com)
 */
@Service
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
        return null;
    }
}
