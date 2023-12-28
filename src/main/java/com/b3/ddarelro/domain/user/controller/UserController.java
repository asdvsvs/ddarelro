package com.b3.ddarelro.domain.user.controller;

import com.b3.ddarelro.domain.user.dto.request.UserSignupReq;
import com.b3.ddarelro.domain.user.dto.request.UsernameUpdateReq;
import com.b3.ddarelro.domain.user.dto.response.UserRes;
import com.b3.ddarelro.domain.user.dto.response.UsernameUpdateRes;
import com.b3.ddarelro.domain.user.service.UserService;
import com.b3.ddarelro.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupReq> signup(@Valid @RequestBody UserSignupReq reqDto) {
        userService.signup(reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> getUseProfile(@PathVariable Long userId) {
        UserRes resDto = userService.getUser(userId);

        return ResponseEntity.ok(resDto);
    }

    @PatchMapping("/username")
    public ResponseEntity<UsernameUpdateRes> updateUsername(
        @Valid @RequestBody UsernameUpdateReq reqDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UsernameUpdateRes resDto =
            userService.updateUsername(userDetails.getUser().getId(), reqDto);

        return ResponseEntity.ok(resDto);
    }
}
