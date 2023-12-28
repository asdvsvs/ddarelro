package com.b3.ddarelro.domain.user.service;


import com.b3.ddarelro.domain.user.dto.request.UserSignupReq;
import com.b3.ddarelro.domain.user.dto.request.UsernameUpdateReq;
import com.b3.ddarelro.domain.user.dto.response.UserRes;
import com.b3.ddarelro.domain.user.dto.response.UsernameUpdateRes;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.exception.UserErrorCode;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (!req.password().equals(req.passwordCheck())) {
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

    @Transactional(readOnly = true)
    public UserRes getUser(final Long userId) {
        User foundUser = findUser(userId);

        return UserRes.builder()
            .id(foundUser.getId())
            .email(foundUser.getEmail())
            .Username(foundUser.getUsername())
            .build();
    }

    @Transactional
    public UsernameUpdateRes updateUsername(final Long userId, final UsernameUpdateReq reqDto) {
        User foundUser = findUser(userId);
        foundUser.updateUsername(reqDto.username());

        return UsernameUpdateRes.builder()
            .username(reqDto.username())
            .build();
    }

    public User findUser(final Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        return user;
    }
}
