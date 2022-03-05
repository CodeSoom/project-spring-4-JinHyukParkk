package com.example.cotobang.interceptor;

import com.example.cotobang.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security로 리펙토링 후 사용하지 않음
 *
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthenticationService authenticationService;

    public AuthenticationInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        return filterWithPathAndMethod(request) ||
                doAuthentication(request, response);
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

    private boolean doAuthentication(HttpServletRequest request,
                                     HttpServletResponse response) throws IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        String accessToken = authorization.substring("Bearer ".length());
        Long userId = authenticationService.paserToken(accessToken);

        return true;
    }
}
