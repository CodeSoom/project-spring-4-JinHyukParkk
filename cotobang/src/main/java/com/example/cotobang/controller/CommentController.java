package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Coin> list() {
        return null;
    }
}
