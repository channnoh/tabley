package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.type.ReservationStatus;
import lombok.*;
import java.time.LocalDateTime;

public class PartnerReservationDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String storeName;
        private String userId;
        private String userPhone;
        private String userEmail;
        private LocalDateTime reservationDate;
        private ReservationStatus reservationStatus;

        public static PartnerReservationDto.Response fromEntity(Reservation reservation) {
            return Response.builder()
                    .storeName(reservation.getStore().getStoreName())
                    .userId(reservation.getMember().getUserId())
                    .userPhone(reservation.getMember().getPhone())
                    .userEmail(reservation.getMember().getEmail())
                    .reservationDate(reservation.getReservationDate())
                    .reservationStatus(reservation.getReservationStatus())
                    .build();
        }


    }
}
