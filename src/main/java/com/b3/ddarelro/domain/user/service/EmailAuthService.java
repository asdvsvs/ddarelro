package com.b3.ddarelro.domain.user.service;

import com.b3.ddarelro.domain.user.entity.EmailAuth;
import com.b3.ddarelro.domain.user.repository.EmailAuthRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthRepository emailAuthRepository;

    public boolean hasMail(final String email) {
        return emailAuthRepository.existsById(email);
    }

    public void delete(final String email) {
        emailAuthRepository.deleteById(email);
    }

    public EmailAuth save(final EmailAuth emailAuth) {
        return emailAuthRepository.save(emailAuth);
    }

    public Optional<EmailAuth> findById(final String email) {
        return emailAuthRepository.findById(email);
    }
}
