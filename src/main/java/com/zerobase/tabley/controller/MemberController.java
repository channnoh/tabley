package com.zerobase.tabley.controller;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.dto.SignInDto;
import com.zerobase.tabley.dto.SignUpDto;
import com.zerobase.tabley.security.TokenProvider;
import com.zerobase.tabley.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @ApiOperation("회원가입")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpDto request) {
        // 회원가입을 위한 API
        Member member = this.memberService.signUp(request);
        log.info("user join -> {}", request.getEmail());
        return ResponseEntity.ok(member);
    }

    @ApiOperation("로그인")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInDto request) {
        // 로그인용 API
        Member member = this.memberService.authenticate(request);
        String token = this.tokenProvider.generateToken(member.getEmail(), member.getMemberType());
        log.info("user login -> {} ", request.getEmail());
        return ResponseEntity.ok(token);
    }
}

