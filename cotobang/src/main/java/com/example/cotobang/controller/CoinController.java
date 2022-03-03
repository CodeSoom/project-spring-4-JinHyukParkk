package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CoinDto;
import com.example.cotobang.service.AuthenticationService;
import com.example.cotobang.service.CoinService;
import com.example.cotobang.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 코인에 대한 요청 처리
 */
@RestController
@RequestMapping("/coins")
public class CoinController {

    private final CoinService coinService;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public CoinController(CoinService coinService,
                          UserService userService,
                          AuthenticationService authenticationService) {
        this.coinService = coinService;
        this.userService = userService;
        this.authenticationService = authenticationService;
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
    public Coin create(@RequestHeader("Authorization") String authorization,
                       @RequestBody @Valid CoinDto coinDto) {
        String accessToken = authorization.substring("Bearer ".length());
        Long userId = authenticationService.paserToken(accessToken);

        User user = userService.getUser(userId);

        return coinService.createCoin(coinDto, user);
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    public Coin update(@RequestHeader("Authorization") String authorization,
                       @PathVariable Long id, @RequestBody CoinDto coinDto) {
        String accessToken = authorization.substring("Bearer ".length());
        Long userId = authenticationService.paserToken(accessToken);

        User user = userService.getUser(userId);

        return coinService.updateCoin(id, coinDto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Coin delete(@RequestHeader("Authorization") String authorization,
                       @PathVariable Long id) {
        String accessToken = authorization.substring("Bearer ".length());
        Long userId = authenticationService.paserToken(accessToken);

        User user = userService.getUser(userId);

        return coinService.deleteCoin(id, user);
    }
}
