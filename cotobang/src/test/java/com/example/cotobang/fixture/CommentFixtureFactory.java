package com.example.cotobang.fixture;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;

public class CommentFixtureFactory {

    public Comment create_댓글(Coin coin , User user) {
        return Comment.builder()
                .comment("테스트 Comment")
                .coin(coin)
                .user(user)
                .build();
    }

    public CommentDto create_댓글_요청_DTO(Long userId) {
        return CommentDto.builder()
                .comment("테스트 CommentDTO")
                .userId(userId)
                .build();
    }
}
