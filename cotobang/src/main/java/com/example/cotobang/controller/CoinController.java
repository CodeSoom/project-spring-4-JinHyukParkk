package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CoinDto;
import com.example.cotobang.service.AuthenticationService;
import com.example.cotobang.service.CoinService;
import com.example.cotobang.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    public CoinController(CoinService coinService,
                          UserService userService) {
        this.coinService = coinService;
        this.userService = userService;
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
    @PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
    public Coin create(@RequestBody @Valid CoinDto coinDto,
                       Authentication authentication) {

        User user = findUserByAuthentication(authentication);

        return coinService.createCoin(coinDto, user);
    }

    @RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
    public Coin update(@PathVariable Long id, @RequestBody CoinDto coinDto,
                       Authentication authentication) {

        User user = findUserByAuthentication(authentication);

        return coinService.updateCoin(id, coinDto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated() and hasAuthority('ADMIN')")
    public Coin delete(@PathVariable Long id,
                       Authentication authentication) {

        User user = findUserByAuthentication(authentication);

        return coinService.deleteCoin(id, user);
    }

    private User findUserByAuthentication(Authentication authentication) {
        Long userId = Long.valueOf(
                String.valueOf(
                        authentication.getPrincipal()));

        return userService.getUser(userId);
    }
}
