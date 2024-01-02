package com.b3.ddarelro.global.jwt.service;


import com.b3.ddarelro.domain.user.service.UserService;
import com.b3.ddarelro.global.jwt.RefreshToken;
import com.b3.ddarelro.global.jwt.TokenProvider;
import com.b3.ddarelro.global.jwt.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public void saveRefreshToken(final String refreshToken, final Long userId) {
        RefreshToken saveToken = RefreshToken.builder()
            .refreshToken(refreshToken)
            .userId(userId)
            .build();
        refreshTokenRepository.save(saveToken);
    }
}
