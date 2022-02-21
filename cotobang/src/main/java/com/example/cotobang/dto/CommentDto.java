package com.example.cotobang.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CommentDto {

    @NotBlank
    private String comment;

    @NotBlank
    @JsonProperty("coin_id")
    private Long coinId;

    @NotBlank
    @JsonProperty("user_id")
    private Long userId;

    @Builder
    public CommentDto(String comment, Long coinId, Long userId) {
        this.comment = comment;
        this.coinId = coinId;
        this.userId = userId;
    }
}
