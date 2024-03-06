package com.zerobase.tabley.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 이메일이 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다");




    private final HttpStatus httpStatus;
    private final String detail;
}
