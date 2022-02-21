package com.example.cotobang.controller;

import com.example.cotobang.dto.ErrorResponse;
import com.example.cotobang.errors.CoinNotFoundException;
import com.example.cotobang.errors.UserEmailDuplicationException;
import com.example.cotobang.errors.UserNotFoundException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFound() {
        return new ErrorResponse("User not found");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserEmailDuplicationException.class)
    public ErrorResponse handleUserEmailDuplication() {
        return new ErrorResponse("User's email is already existed");
    }
}
