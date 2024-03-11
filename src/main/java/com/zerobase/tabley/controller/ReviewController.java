package com.zerobase.tabley.controller;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.dto.DeleteReviewDto;
import com.zerobase.tabley.dto.UpdateReviewDto;
import com.zerobase.tabley.dto.WriteReviewDto;
import com.zerobase.tabley.service.ReviewService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @ApiOperation("리뷰 작성 API 입니다.")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/write/{reservationId}")
    public ResponseEntity<?> writeReview(@PathVariable Long reservationId,
                                         @RequestBody WriteReviewDto.Request request,
                                         @AuthenticationPrincipal Member user) {
        WriteReviewDto.Response writtenReview = reviewService.writeReview(reservationId, request, user);

        return ResponseEntity.ok(writtenReview);
    }

    @ApiOperation("리뷰 수정 API 입니다.")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{reservationId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reservationId,
                                          @RequestBody UpdateReviewDto.Request request,
                                          @AuthenticationPrincipal Member user) {
        UpdateReviewDto.Response updatedReview = reviewService.updateReview(reservationId, request, user);

        return ResponseEntity.ok(updatedReview);
    }

    @ApiOperation("리뷰 삭제 API 입니다.")
    @PreAuthorize("hasRole('USER') or hasRole('PARTNER')")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId,
                                          @AuthenticationPrincipal Member user) {
        DeleteReviewDto deleteReviewResponse = reviewService.deleteReview(reviewId, user);

        return ResponseEntity.ok(deleteReviewResponse);
    }


}
