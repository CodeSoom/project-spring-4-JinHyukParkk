package com.example.cotobang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessionResponseData {

    String accessToken;

    @Builder
    public SessionResponseData(String accessToken) {
        this.accessToken = accessToken;
    }
}
