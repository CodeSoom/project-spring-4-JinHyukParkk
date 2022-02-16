package com.example.cotobang.respository;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCoin(Coin coin);
}
