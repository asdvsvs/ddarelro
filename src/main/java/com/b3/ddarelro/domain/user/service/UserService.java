package com.b3.ddarelro.domain.user.service;


import com.b3.ddarelro.domain.user.dto.request.UserPasswordUpdateReq;
import com.b3.ddarelro.domain.user.dto.request.UserSignupReq;
import com.b3.ddarelro.domain.user.dto.request.UsernameUpdateReq;
import com.b3.ddarelro.domain.user.dto.response.UserDeleteRes;
import com.b3.ddarelro.domain.user.dto.response.UserPasswordUpdateRes;
import com.b3.ddarelro.domain.user.dto.response.UserRes;
import com.b3.ddarelro.domain.user.dto.response.UsernameUpdateRes;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.exception.UserErrorCode;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import com.b3.ddarelro.domain.userboard.repository.UserBoardRepository;
import com.b3.ddarelro.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(final UserSignupReq reqDto) {

        if (userRepository.existsByEmail(reqDto.email())) {
            throw new GlobalException(UserErrorCode.EXISTS_EMAIL);
        }

        if (!reqDto.password().equals(reqDto.passwordCheck())) {
            throw new GlobalException(UserErrorCode.MISMATCH_PASSWORD);
        }

        String encryptionPassword = passwordEncoder.encode(reqDto.password());

        User user = User.builder()
            .email(reqDto.email())
            .username(reqDto.username())
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

    @Transactional
    public UserPasswordUpdateRes updatePassword(final Long userId,
        final UserPasswordUpdateReq reqDto) {
        User foundUser = findUser(userId);
        if (!reqDto.password().equals(reqDto.passwordCheck())) {
            throw new GlobalException(UserErrorCode.MISMATCH_PASSWORD);
        }
        String encryptionPassword = passwordEncoder.encode(reqDto.password());
        foundUser.updatePassword(encryptionPassword);
        return UserPasswordUpdateRes.builder()
            .message("비밀 번호 변경에 성공했습니다.")
            .build();
    }

    /**
     * 회원탈퇴시 Soft Delete 처리를 하고 현재 참여하고 있는 보드 중 팀장 직급인 보드가 있으면 예외를 던진다.
     */
    // Todo: 일주일이 지나면 스케줄러를 돌려 email과 비밀번호를 임의의 값으로 수정한다.
    // Todo: 1개월이 지나면 HardDelete를 한다.
    @Transactional
    public UserDeleteRes deleteUser(final Long userId) {
        User foundUser = findUser(userId);

        validateDeleteUser(foundUser);

        foundUser.delete();
        return UserDeleteRes.builder()
            .message("탈퇴가 완료 되었습니다")
            .build();
    }

    public User findUser(final Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
        if (user.getDeleted()) {
            throw new GlobalException(UserErrorCode.DELETED_USER);
        }
        return user;
    }

    private void validateDeleteUser(User user) {
        if (userBoardRepository.countAdminUserBoards(user) > 0) {
            throw new GlobalException(UserErrorCode.HAS_ADMIN_BOARD);
        }
    }
}
