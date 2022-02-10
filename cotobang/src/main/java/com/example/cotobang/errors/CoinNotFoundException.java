package com.example.cotobang.errors;

public class CoinNotFoundException extends RuntimeException {

    public CoinNotFoundException(Long id) {
        super("Coin not found : " + id);
    }
}
