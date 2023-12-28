package com.b3.ddarelro.domain.user.service;


import com.b3.ddarelro.domain.user.dto.request.UserSignupReq;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.exception.UserErrorCode;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(final UserSignupReq req) {

        if (userRepository.existsByEmail(req.email())) {
            throw new GlobalException(UserErrorCode.EXISTS_EMAIL);
        }

        if (userRepository.existsByUsername(req.username())) {
            throw new GlobalException(UserErrorCode.EXISTS_NICKNAME);
        }

        if (!req.password().equals(req.passwordCheck())){
            throw new GlobalException(UserErrorCode.MISMATCH_PASSWORD);
        }

        String encryptionPassword = passwordEncoder.encode(req.password());

        User user = User.builder()
            .email(req.email())
            .username(req.username())
            .password(encryptionPassword)
            .build();

        userRepository.save(user);
    }

    public User findUser(final Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        return user;
    }
}