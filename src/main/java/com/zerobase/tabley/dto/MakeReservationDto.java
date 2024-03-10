package com.zerobase.tabley.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.type.ReservationStatus;
import lombok.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MakeReservationDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank(message = "Reservation date is required")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime reservationDate;


        public static Reservation toEntity(MakeReservationDto.Request request, Store store, Member member) {
            return Reservation.builder()
                    .store(store)
                    .member(member)
                    .reservationDate(request.getReservationDate())
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String storeName;
        private String storeAddress;
        private String contact;
        private LocalDateTime reservationDate;
        private ReservationStatus reservationStatus;

        public static MakeReservationDto.Response fromEntity(Reservation reservation) {
            return MakeReservationDto.Response.builder()
                    .storeName(reservation.getStore().getStoreName())
                    .storeAddress(reservation.getStore().getStoreAddress())
                    .contact(reservation.getStore().getContact())
                    .reservationDate(reservation.getReservationDate())
                    .reservationStatus(reservation.getReservationStatus())
                    .build();
        }
    }
}
