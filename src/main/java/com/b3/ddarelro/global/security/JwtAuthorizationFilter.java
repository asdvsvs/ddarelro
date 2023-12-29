package com.b3.ddarelro.global.security;

import com.b3.ddarelro.global.exception.ErrorResponse;
import com.b3.ddarelro.global.jwt.JwtUtil;
import com.b3.ddarelro.global.security.exception.SecurityErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = jwtUtil.getTokenFromHeader(request, JwtUtil.ACCESS_TOKEN_HEADER);

        //TODO: AccessToken 만료시 RefreshToken을 사용하여 AccessToken 재발급

        if (StringUtils.hasText(accessToken)) {
            if (!jwtUtil.validateToken(accessToken)) {
                log.error("Token Error");
                // 토큰이 유효하지 않음을 JSON 형식으로 응답 메시지, 상태코드 생성
                sendErrorResponse(response,
                    SecurityErrorCode.INVALID_TOKEN.getHttpStatus().value(),
                    SecurityErrorCode.INVALID_TOKEN.getMessage());
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(accessToken);

            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error("Authentication error: {}", e.getMessage());
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
