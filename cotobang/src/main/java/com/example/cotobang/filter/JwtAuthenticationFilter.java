package com.example.cotobang.filter;

import com.example.cotobang.errors.InvalidAccessTokenException;
import com.example.cotobang.security.UserAuthentication;
import com.example.cotobang.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final AuthenticationService authenticationService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationService authenticationService) {
        super(authenticationManager);
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        if (filterWithPathAndMethod(request)) {
            chain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization != null) {
            String accessToken = authorization.substring("Bearer ".length());

            Long userId = authenticationService.paserToken(accessToken);

            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UserAuthentication(userId);
            context.setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    private boolean filterWithPathAndMethod(HttpServletRequest request) {
        String path = request.getRequestURI();

        if (!path.equals("/coins")) {
            return true;
        }
        String method = request.getMethod();

        if (method.equals("GET")) {
            return true;
        }

        return false;
    }
}
