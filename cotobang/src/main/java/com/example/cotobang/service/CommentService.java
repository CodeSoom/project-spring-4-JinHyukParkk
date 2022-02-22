package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;
import com.example.cotobang.errors.CoinNotFoundException;
import com.example.cotobang.errors.UserNotFoundException;
import com.example.cotobang.respository.CoinRepository;
import com.example.cotobang.respository.CommentRepository;
import com.example.cotobang.respository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final CoinRepository coinRepository;

    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository,
                          CoinRepository coinRepository,
                          UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.coinRepository = coinRepository;
        this.userRepository = userRepository;
    }

    public List<Comment> getComments(Coin coin) {
        return commentRepository.findByCoin(coin);
    }

    public Comment createComment(CommentDto commentDto) {
        Long coinId = commentDto.getCoinId();
        Long userId = commentDto.getUserId();

        Coin coin = coinRepository.findById(coinId)
                .orElseThrow(() -> new CoinNotFoundException(coinId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .coin(coin)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }
}
