package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.respository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    private final CoinService coinService;

    public CommentService(CommentRepository commentRepository, CoinService coinService) {
        this.commentRepository = commentRepository;
        this.coinService = coinService;
    }

    public List<Comment> getComments(Long coinId) {
        Coin coin = coinService.getCoin(coinId);

        return commentRepository.findByCoin(coin);
    }
}
