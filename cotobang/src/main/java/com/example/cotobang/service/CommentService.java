package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;
import com.example.cotobang.errors.NotAuthorityException;
import com.example.cotobang.respository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getComments(Coin coin) {
        return commentRepository.findByCoin(coin);
    }

    public Comment createComment(CommentDto commentDto,
                                 Coin coin,
                                 User user) {
        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .coin(coin)
                .user(user)
                .build();

        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id,
                                 CommentDto commentDto,
                                 Coin coin,
                                 User user) {
        Comment comment = getComment(id);

        if (!coin.compare(comment.getCoin())) {
            throw new NotAuthorityException(coin.getKoreanName());
        }

        if (!user.compare(comment.getUser())) {
            throw new NotAuthorityException(user.getName());
        }

        comment.change(
                commentDto.getComment());

        return comment;
    }

    public Comment deleteComment(Long id, User user) {
        Comment comment = getComment(id);

        if (!user.compare(comment.getUser())) {
            throw new NotAuthorityException(user.getName());
        }

        commentRepository.delete(comment);

        return comment;
    }

    private Comment getComment(Long id) {
        return commentRepository.getById(id);
    }
}
