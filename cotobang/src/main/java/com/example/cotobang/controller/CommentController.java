package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.service.CoinService;
import com.example.cotobang.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    private final CoinService coinService;

    public CommentController(CommentService commentService, CoinService coinService) {
        this.commentService = commentService;
        this.coinService = coinService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> list(@PathVariable Long id) {
        Coin coin = coinService.getCoin(id);

        return commentService.getComments(coin);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create() {
        return null;
    }
}
