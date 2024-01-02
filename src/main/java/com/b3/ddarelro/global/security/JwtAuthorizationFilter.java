package com.b3.ddarelro.global.security;

import static com.b3.ddarelro.global.jwt.TokenProvider.ACCESS_TOKEN_HEADER;
import static com.b3.ddarelro.global.jwt.TokenProvider.BEARER_PREFIX;
import static com.b3.ddarelro.global.jwt.TokenProvider.REFRESH_TOKEN_HEADER;

import com.b3.ddarelro.global.exception.ErrorResponse;
import com.b3.ddarelro.global.exception.GlobalException;
import com.b3.ddarelro.global.jwt.TokenProvider;
import com.b3.ddarelro.global.jwt.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "AccessToken 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = tokenProvider.getTokenFromHeader(request, ACCESS_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken) && !tokenProvider.validateToken(accessToken)) {
            String refreshToken = tokenProvider.getTokenFromHeader(request, REFRESH_TOKEN_HEADER);

            try {
                accessToken = tokenService.generateAccessToken(refreshToken);
            } catch (GlobalException e) {
                log.error("RefreshToken Error");
                sendErrorResponse(
                    response,
                    e.getErrorCode().getHttpStatus().value(),
                    e.getErrorCode().getMessage()
                );
                return;
            }
            response.addHeader(ACCESS_TOKEN_HEADER, BEARER_PREFIX + accessToken);
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = tokenProvider.getUserInfoFromToken(accessToken);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error("Authentication error: {}", e.getMessage());
                sendErrorResponse(response, HttpStatus.SC_BAD_REQUEST, "만료된 토큰입니다.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    // 응답 메세지 생성
    private void sendErrorResponse(HttpServletResponse response, int status, String message)
        throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
            .status(status)
            .build();
        errorResponse.addMessage(message);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorResponse.getStatus());
        try {
            response.getWriter().println(new ObjectMapper().writeValueAsString(errorResponse));
        } catch (IOException e) {
            log.error("Response writing failed: {}", e.getMessage());
        }
    }
}
