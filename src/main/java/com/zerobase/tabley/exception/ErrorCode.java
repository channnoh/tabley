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
    RESERVATION_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 예약입니다."),
    ALREADY_REGISTER_STORE_USER(HttpStatus.BAD_REQUEST, "이 사용자는 이미 매장을 등록했습니다."),
    ALREADY_EXISTS_STORE(HttpStatus.BAD_REQUEST, "이미 존재하는 매장 상호입니다."),
    ACCESS_DENIED_RESERVATION_APPROVAL(HttpStatus.BAD_REQUEST, "해당 매장의 예약을 승인할 수 있는 권한이 없습니다."),
    RESERVATION_ALREADY_FULL(HttpStatus.BAD_REQUEST, "예약이 가득 차있습니다."),
    RESERVATION_NOT_FOUND_FOR_DATE(HttpStatus.BAD_REQUEST, "해당 날짜에 예약이 없습니다."),
    WRONG_RESERVATION_DATETIME(HttpStatus.BAD_REQUEST, "예약 날짜가 올바르지 않습니다."),
    WRONG_RESERVATION_USER_INFORMATION(HttpStatus.BAD_REQUEST, "예약자 정보가 일치하지 않습니다."),
    UNAPPROVED_RESERVATION_BY_OWNER(HttpStatus.BAD_REQUEST, "매장 점주로 부터 승인되지 않은 예약입니다."),
    ALREADY_CONFIRMED_RESERVATION(HttpStatus.BAD_REQUEST, "이미 방문 확인이 완료된 예약입니다."),
    RESERVATION_CHECK_IN_TIME_EXPIRED(HttpStatus.BAD_REQUEST, "예약 방문확인 시간이 지났습니다.");







    private final HttpStatus httpStatus;
    private final String detail;
}
