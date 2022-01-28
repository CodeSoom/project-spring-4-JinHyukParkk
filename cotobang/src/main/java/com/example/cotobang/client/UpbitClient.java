package com.example.cotobang.client;

import com.example.cotobang.client.dto.UpbitCoinResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 업비트 Open API 사용히는 Client
 *
 * @author Hyuk (pjh08190819@naver.com)
 */
@Component
public class UpbitClient {

    private static final String UPBIT_URL = "https://api.upbit.com/v1/market/all";

    public List<UpbitCoinResponseDto> list() {
        URI uri = UriComponentsBuilder.fromUriString(UPBIT_URL)
                .queryParam("isDetails", "false")
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        RequestEntity<Void> request = RequestEntity.get(uri)
                .build();

        ResponseEntity<List<UpbitCoinResponseDto>> responseEntity =
                new RestTemplate().exchange(
                        request,
                        new ParameterizedTypeReference<>() {}
                );

        return responseEntity.getBody();
    }
}
