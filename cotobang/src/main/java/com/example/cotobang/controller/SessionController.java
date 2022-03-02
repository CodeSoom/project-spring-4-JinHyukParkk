package com.example.cotobang.controller;

import com.example.cotobang.dto.SessionRequestData;
import com.example.cotobang.dto.SessionResponseData;
import com.example.cotobang.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final AuthenticationService authenticationService;

    public SessionController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponseData login(@RequestBody SessionRequestData sessionRequestData) {
        String accessToken = authenticationService.login(sessionRequestData);

        return SessionResponseData.builder()
                .accessToken(accessToken)
                .build();
    }
}
