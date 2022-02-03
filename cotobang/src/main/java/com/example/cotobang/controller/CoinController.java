package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.dto.CoinRequestDto;
import com.example.cotobang.service.CoinService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 코인에 대한 요청 처리
 */
@RestController
@RequestMapping("/coins")
public class CoinController {

    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    /**
     * 등록된 전체 코인 리스트를 반환합니다.
     *
     * @return 전체 코인 리스트
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Coin> list() {
        return coinService.getCoins();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Coin create(@RequestBody Coin coin) {
        return coinService.createCoin(coin);
    }
}
