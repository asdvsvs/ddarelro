package com.b3.ddarelro.global.security;

import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.entity.UserStatus;
import com.b3.ddarelro.domain.user.exception.UserErrorCode;
import com.b3.ddarelro.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(
                () -> new IllegalArgumentException(UserErrorCode.NOT_FOUND_USER.getMessage()));
        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new IllegalArgumentException(UserErrorCode.NOT_FOUND_USER.getMessage());
        }
        return new UserDetailsImpl(user);
    }
}
