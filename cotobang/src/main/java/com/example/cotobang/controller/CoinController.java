package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 코인에 대한 요청 처리
 *
 * @author Hyuk (pjh08190819@naver.com)
 */
@RestController
@RequestMapping("/coins")
public class CoinController {

    /**
     * 등록된 전체 코인 리스트를 반환합니다.
     *
     * @return 전체 코인 리스트
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Coin> list() {
        return null;
    }
}
