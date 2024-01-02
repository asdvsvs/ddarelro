package com.b3.ddarelro.global.jwt.service;


import com.b3.ddarelro.global.exception.GlobalException;
import com.b3.ddarelro.global.jwt.RefreshToken;
import com.b3.ddarelro.global.jwt.TokenProvider;
import com.b3.ddarelro.global.jwt.exception.TokenErrorCode;
import com.b3.ddarelro.global.jwt.repository.RefreshTokenRepository;
import com.b3.ddarelro.global.security.UserDetailsImpl;
import com.b3.ddarelro.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final UserDetailsServiceImpl userDetailsService;


    public void saveRefreshToken(final String refreshToken, final Long userId) {
        RefreshToken saveToken = RefreshToken.builder()
            .refreshToken(refreshToken)
            .userId(userId)
            .build();
        refreshTokenRepository.save(saveToken);
    }

    public Long findUserByToken(final String refreshToken) {
        return refreshTokenRepository.findUserIdById(refreshToken);
    }

    public boolean hasRefreshToken(final String refreshToken) {
        return refreshTokenRepository.existsById(refreshToken);
    }

    public String generateAccessToken(final String refreshToken) {
        RefreshToken foundRefreshToken = refreshTokenRepository.findById(refreshToken)
            .orElseThrow(() -> new GlobalException(TokenErrorCode.INVALID_TOKEN));

        UserDetailsImpl userDetails = userDetailsService.loadUserById(
            foundRefreshToken.getUserId());

        return tokenProvider.createAccessToken(userDetails.getEmail()).split(" ")[1].trim();
    }
}
