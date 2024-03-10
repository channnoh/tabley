package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.type.ReservationStatus;
import lombok.*;

import java.time.LocalDateTime;

public class ApproveReservationDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private ReservationStatus reservationStatus;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String storeName;
        private LocalDateTime reservationDate;

        public static ApproveReservationDTO.Response fromEntity(Reservation reservation) {
            return ApproveReservationDTO.Response.builder()
                    .storeName(reservation.getStore().getStoreName())
                    .reservationDate(reservation.getReservationDate())
                    .build();
        }
    }

}
