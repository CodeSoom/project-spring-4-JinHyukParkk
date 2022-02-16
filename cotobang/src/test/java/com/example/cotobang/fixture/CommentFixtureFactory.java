package com.example.cotobang.fixture;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;

public class CommentFixtureFactory {

    public Comment create_댓글(Coin coin , User user) {
        return Comment.builder()
                .comment("테스트")
                .coin(coin)
                .user(user)
                .build();
    }
}
