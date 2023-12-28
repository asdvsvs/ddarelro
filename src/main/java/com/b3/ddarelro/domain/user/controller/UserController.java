package com.b3.ddarelro.domain.user.controller;

import com.b3.ddarelro.domain.user.dto.request.UserSignupReq;
import com.b3.ddarelro.domain.user.dto.response.UserRes;
import com.b3.ddarelro.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<UserSignupReq> signup(@RequestBody @Valid UserSignupReq req) {
        userService.signup(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserRes> getUseProfile(@PathVariable Long userId) {
        UserRes res = userService.getUser(userId);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
}
