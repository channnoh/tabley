package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Review;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteReviewDto {

    private String message;

    public static DeleteReviewDto from(Review review) {
        return DeleteReviewDto.builder()
                .message(review.getStore().getStoreName() + " 매장 리뷰 삭제가 완료되었습니다.")
                .build();
    }
}
