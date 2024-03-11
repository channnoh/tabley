package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Review;
import lombok.*;

import javax.persistence.Lob;

public class UpdateReviewDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private Double rating;

        @Lob
        private String content;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String message;

        public static UpdateReviewDto.Response from(Review review) {
            return UpdateReviewDto.Response.builder()
                    .message(review.getStore().getStoreName() + " 매장 리뷰 수정이 완료되었습니다.")
                    .build();
        }
    }

}
