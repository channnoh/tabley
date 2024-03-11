package com.zerobase.tabley.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

public class ConfirmVisitDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private String phoneNumber;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private final LocalDateTime arrivalTime = LocalDateTime.now();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String message;

        public static ConfirmVisitDto.Response fromEntity(String storeName) {
            return Response.builder()
                    .message(storeName + " 매장 예약 방문이 완료되었습니다.")
                    .build();
        }
    }
}
