package com.example.cotobang.service;

import com.example.cotobang.domain.User;
import com.example.cotobang.dto.SessionRequestData;
import com.example.cotobang.errors.LoginFailException;
import com.example.cotobang.respository.UserRepository;
import com.example.cotobang.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public AuthenticationService(UserRepository userRepository,
                                 JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String login(SessionRequestData sessionRequestData) {
        final String email = sessionRequestData.getEmail();
        final String password = sessionRequestData.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginFailException(email));

        if (!user.authenticate(password)) {
            throw new LoginFailException(email);
        }

        return jwtUtil.encode(user.getId());
    }
}
