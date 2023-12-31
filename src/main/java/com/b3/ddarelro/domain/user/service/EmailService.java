package com.b3.ddarelro.domain.user.service;

import com.b3.ddarelro.domain.user.dto.request.EmailAuthSendReq;
import com.b3.ddarelro.domain.user.dto.request.EmailCodeCheckReq;
import com.b3.ddarelro.domain.user.dto.response.EmailAuthSendRes;
import com.b3.ddarelro.domain.user.dto.response.EmailCodeCheckRes;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.user.exception.EmailErrorCode;
import com.b3.ddarelro.global.email.EmailUtil;
import com.b3.ddarelro.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailUtil emailUtil;
    private final UserService userService;

    @Transactional
    public EmailAuthSendRes sendEmail(final EmailAuthSendReq reqDto) {
        emailUtil.sendMessage(reqDto.email(), "ddarelro [이메일 인증]");
        return EmailAuthSendRes.builder().message("인증코드가 발송되었습니다.").build();
    }

    @Transactional
    public EmailCodeCheckRes checkAuthCode(final EmailCodeCheckReq reqDto) {
        User user = userService.findUserByEmail(reqDto.email());
        if (!emailUtil.checkCode(reqDto.email(), reqDto.code())) {
            throw new GlobalException(EmailErrorCode.NOT_MATCHED_CODE);
        }
        user.updateStatus();
        return EmailCodeCheckRes.builder()
            .message("인증이 완료되었습니다.")
            .build();
    }
}
