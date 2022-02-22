package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;
import com.example.cotobang.service.CoinService;
import com.example.cotobang.service.CommentService;
import com.example.cotobang.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final CoinService coinService;
    private final UserService userService;

    public CommentController(CommentService commentService,
                             CoinService coinService,
                             UserService userService) {
        this.commentService = commentService;
        this.coinService = coinService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Comment> list(@RequestParam("coin_id") Long coinId) {
        Coin coin = coinService.getCoin(coinId);

        return commentService.getComments(coin);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody CommentDto commentDto) {
        Coin coin = coinService.getCoin(commentDto.getCoinId());
        User user = userService.getUser(commentDto.getUserId());

        return commentService.createComment(commentDto, coin, user);
    }
}
