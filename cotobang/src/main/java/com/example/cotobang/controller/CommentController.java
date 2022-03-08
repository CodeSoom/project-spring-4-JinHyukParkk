package com.example.cotobang.controller;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;
import com.example.cotobang.service.CoinService;
import com.example.cotobang.service.CommentService;
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
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Comment create(@RequestBody CommentDto commentDto,
                          Authentication authentication) {
        Coin coin = coinService.getCoin(commentDto.getCoinId());
        User user = findUserByAuthentication(authentication);

        return commentService.createComment(commentDto, coin, user);
    }

    @RequestMapping(path = "{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Comment update(@PathVariable Long id,
                          @RequestBody CommentDto commentDto,
                          Authentication authentication) {
        Coin coin = coinService.getCoin(commentDto.getCoinId());
        User user = findUserByAuthentication(authentication);

        return commentService.updateComment(id, commentDto, coin, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Comment delete(@PathVariable Long id,
                          Authentication authentication) {

        User user = findUserByAuthentication(authentication);

        return commentService.deleteComment(id, user);
    }

    private User findUserByAuthentication(Authentication authentication) {
        Long userId = Long.valueOf(
                String.valueOf(
                        authentication.getPrincipal()));

        return userService.getUser(userId);
    }
}
