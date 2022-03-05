package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.SessionRequestData;
import com.example.cotobang.errors.InvalidAccessTokenException;
import com.example.cotobang.errors.LoginFailException;
import com.example.cotobang.respository.UserRepository;
import com.example.cotobang.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 JwtUtil jwtUtil,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(SessionRequestData sessionRequestData) {
        final String email = sessionRequestData.getEmail();
        final String password = sessionRequestData.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailException(email));

        if (!user.authenticate(password, passwordEncoder)) {
            throw new LoginFailException(email);
        }

        return jwtUtil.encode(user.getId());
    }

    public Long paserToken(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            throw new InvalidAccessTokenException(accessToken);
        }
        Claims claims = jwtUtil.decode(accessToken);

        return claims.get("userId", Long.class);
    }
}
