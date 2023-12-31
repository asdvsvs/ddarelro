package com.b3.ddarelro.global.email;

import com.b3.ddarelro.domain.user.entity.EmailAuth;
import com.b3.ddarelro.domain.user.exception.EmailErrorCode;
import com.b3.ddarelro.domain.user.service.EmailAuthService;
import com.b3.ddarelro.global.exception.GlobalException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Slf4j(topic = "email 인증코드 생성 및 발송")
@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final EmailAuthService emailAuthService;

    @Value("${spring.mail.username}")
    private String email;

    public String createAuthCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void sendMessage(String to, String subject) {
        try {
            String code = createAuthCode();
            MimeMessage message = createMessage(to, subject, code);

            if (emailAuthService.hasMail(to)) {
                emailAuthService.delete(to);
            }
            EmailAuth emailAuth = EmailAuth.builder()
                .email(to)
                .code(code)
                .build();

            emailAuthService.save(emailAuth);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warn(e.getMessage());
        }
    }

    public boolean checkCode(String to, String code) {
        try {
            EmailAuth emailAuth = emailAuthService.findById(to)
                .orElseThrow(() -> new GlobalException(
                    EmailErrorCode.NOT_FOUND_EMAIL));

            return emailAuth.getCode().equals(code);
        } catch (Exception e) {
            throw new GlobalException(EmailErrorCode.EXPIRED_CODE);
        }
    }

    private MimeMessage createMessage(String to, String subject, String code)
        throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(email);
        message.addRecipients(RecipientType.TO, to);
        message.setSubject(subject);
        message.setText(createMailHtml(code), "UTF-8", "html");

        return message;
    }

    private String createMailHtml(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("email", context);
    }

}
