package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Review;
import lombok.*;

import javax.persistence.Lob;

public class WriteReviewDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private Double rating;

        @Lob
        private String content;

        public static Review toEntity(WriteReviewDto.Request request, Reservation reservation) {
            return Review.builder()
                    .rating(request.getRating())
                    .content(request.getContent())
                    .store(reservation.getStore())
                    .reservation(reservation)
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String message;

        public static WriteReviewDto.Response fromEntity(Review review) {
            return WriteReviewDto.Response.builder()
                    .message(review.getStore().getStoreName() + " 매장 리뷰 작성이 완료되었습니다.")
                    .build();
        }
    }

}
