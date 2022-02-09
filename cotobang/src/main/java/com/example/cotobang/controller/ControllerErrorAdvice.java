package com.example.cotobang.controller;

import com.example.cotobang.dto.ErrorResponse;
import com.example.cotobang.errors.CoinNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class ControllerErrorAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CoinNotFoundException.class)
    public ErrorResponse handleCoinNotFound() {
        return new ErrorResponse("Coin not found");
    }
}
