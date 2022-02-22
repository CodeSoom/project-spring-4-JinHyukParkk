package com.example.cotobang.service;

import com.example.cotobang.domain.Coin;
import com.example.cotobang.domain.Comment;
import com.example.cotobang.domain.User;
import com.example.cotobang.dto.CommentDto;
import com.example.cotobang.fixture.CoinFixtureFactory;
import com.example.cotobang.fixture.CommentFixtureFactory;
import com.example.cotobang.fixture.UserFixtureFactory;
import com.example.cotobang.respository.CoinRepository;
import com.example.cotobang.respository.CommentRepository;
import com.example.cotobang.respository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("CommentService 클래스")
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CoinRepository coinRepository;

    @Autowired
    UserRepository userRepository;

    CoinFixtureFactory coinFixtureFactory;

    UserFixtureFactory userFixtureFactory;

    CommentFixtureFactory commentFixtureFactory;

    Coin coin;

    User user;

    @BeforeEach
    void setUp() {
        coinFixtureFactory = new CoinFixtureFactory();
        userFixtureFactory = new UserFixtureFactory();
        commentFixtureFactory = new CommentFixtureFactory();

        coin = coinRepository.save(coinFixtureFactory.create_코인());
        user = userRepository.save(userFixtureFactory.create_사용자());
    }

    @Nested
    @DisplayName("getComments() 메소드는")
    class Describe_getComments {

        @Nested
        @DisplayName("coin id가 주어진다면")
        class Context_with_coin_id {

            Coin givenCoin;

            @BeforeEach
            void prepare() {
                givenCoin = coin;

                Comment comment = commentFixtureFactory.create_댓글(givenCoin, user);

                commentRepository.save(comment);
            }

            @Test
            @DisplayName("조회된 comment 리스트를 응답합니다.")
            void it_return_comments() throws Exception {
                assertThat(commentService.getComments(givenCoin))
                        .hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("createComment() 메소드는")
    class Describe_createComment {

        @Nested
        @DisplayName("Comment 정보와 coin id와 user id가 주어진다면")
        class Context_with_comment_and_coin_id_and_user_id {

            CommentDto givenCommentDto;

            @BeforeEach
            void prepare() {
                givenCommentDto = commentFixtureFactory.create_댓글_요청_DTO(user.getId());
            }

            @Test
            @DisplayName("Comment를 생성하고 리턴합니다.")
            void it_create_comment_reture_comment() {
                Comment comment = commentService.createComment(givenCommentDto, coin, user);

                assertThat(comment).isNotNull();
                assertThat(comment.getComment())
                        .isEqualTo(givenCommentDto.getComment());
            }
        }
    }
}
