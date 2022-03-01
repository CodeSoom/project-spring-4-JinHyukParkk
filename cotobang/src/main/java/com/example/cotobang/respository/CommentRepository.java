package com.example.cotobang.respository;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.coin join fetch c.user")
    List<Comment> findByCoin(Coin coin);
}
