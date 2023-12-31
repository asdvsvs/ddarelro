package com.b3.ddarelro.global.security;

import static com.b3.ddarelro.global.jwt.TokenProvider.ACCESS_TOKEN_HEADER;
import static com.b3.ddarelro.global.jwt.TokenProvider.REFRESH_TOKEN_HEADER;

import com.b3.ddarelro.domain.user.dto.request.UserLoginReq;
import com.b3.ddarelro.domain.user.dto.response.UserLoginRes;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.global.jwt.TokenProvider;
import com.b3.ddarelro.global.jwt.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 AccessToken RefreshToken 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    public JwtAuthenticationFilter(TokenProvider tokenProvider, TokenService tokenService) {
        this.tokenProvider = tokenProvider;
        this.tokenService = tokenService;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginReq req = new ObjectMapper().readValue(request.getInputStream(),
                UserLoginReq.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    req.email(),
                    req.password()
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) {

        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();

        String accessToken = tokenProvider.createAccessToken(user.getEmail());
        String refreshToken = tokenProvider.createRefreshToken(user.getEmail());

        response.addHeader(ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(REFRESH_TOKEN_HEADER, refreshToken);

        refreshToken = refreshToken.split(" ")[1].trim();

        tokenService.saveRefreshToken(refreshToken, user.getId());

        sendResponse(response, "로그인에 성공했습니다.");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
        sendResponse(response, "로그인에 실패했습니다.");
    }

    // 응답 메세지 생성
    private void sendResponse(HttpServletResponse response, String message) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        UserLoginRes loginRes = UserLoginRes.builder().message(message).build();

        try {
            response.getWriter().println(new ObjectMapper().writeValueAsString(loginRes));
        } catch (IOException e) {
            log.error("Response writing failed: {}", e.getMessage());
        }
    }
}