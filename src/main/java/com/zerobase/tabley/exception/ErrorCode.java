package com.zerobase.tabley.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ALREADY_REGISTER_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "일치하는 아이디가 없습니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    STORE_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 매장입니다."),
    ALREADY_REGISTER_STORE_USER(HttpStatus.BAD_REQUEST, "이 사용자는 이미 매장을 등록했습니다."),
    ALREADY_EXISTS_STORE(HttpStatus.BAD_REQUEST, "이미 존재하는 매장 상호입니다. "),
    RESERVATION_ALREADY_FULL(HttpStatus.BAD_REQUEST, "예약이 가득 차있습니다. ");




    private final HttpStatus httpStatus;
    private final String detail;
}
