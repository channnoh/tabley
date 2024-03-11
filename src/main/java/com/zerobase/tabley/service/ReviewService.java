package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Review;
import com.zerobase.tabley.dto.UpdateReviewDto;
import com.zerobase.tabley.dto.WriteReviewDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.repository.ReservationRepository;
import com.zerobase.tabley.repository.ReviewRepository;
import com.zerobase.tabley.type.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.zerobase.tabley.exception.ErrorCode.*;
import static com.zerobase.tabley.type.ReviewStatus.NOT_WRITTEN;
import static com.zerobase.tabley.type.ReviewStatus.WRITTEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    /**
     * 리뷰작성 메서드
     * 1. 예약 존재 여부
     * 2. 예약 후 매장 사용여부
     * 3. 예약자와 리뷰 작성하려는 유저 같은지
     * 4. 예약 날짜 후 3일이 지났는지
     */

    @Transactional
    public WriteReviewDto.Response writeReview(Long reservationId,
                                               WriteReviewDto.Request writeReviewRequest,
                                               Member user) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        if (reservation.getReservationStatus() != ReservationStatus.VISIT_CONFIRMED) {
            throw new CustomException(ACCESS_DENIED_REVIEW_AFTER_RESERVATION);
        }

        if (!Objects.equals(reservation.getMember().getId(), user.getId())) {
            throw new CustomException(ACCESS_DENIED_REVIEW_WRITE);
        }

        if (LocalDateTime.now().isAfter(reservation.getReservationDate().plusDays(3))){
            throw new CustomException(REVIEW_WRITE_PERIOD_EXPIRED);
        }

        Review review = WriteReviewDto.Request.toEntity(writeReviewRequest, reservation);
        Review savedReview = reviewRepository.save(review);

        reservation.setReview(savedReview);

        savedReview.getStore().updateRating();
        savedReview.getReservation().setReviewStatus(WRITTEN);

        return WriteReviewDto.Response.fromEntity(savedReview);
    }

    /**
     * 리뷰 수정 메서드
     * 1. 예약 존재 여부
     * 2. 리뷰 작성 여부
     * 3. 기존 리뷰와 변경 여부
     */

    @Transactional
    public UpdateReviewDto.Response updateReview(Long reservationId,
                                                 UpdateReviewDto.Request updateReviewRequest,
                                                 Member user) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        Review review = reservation.getReview();

        if (reservation.getReviewStatus() == NOT_WRITTEN) {
            throw new CustomException(REVIEW_NOT_FOUND);
        }

        if ((Objects.equals(review.getRating(), updateReviewRequest.getRating())) && (review.getContent().equals(updateReviewRequest.getContent()))) {
            throw new CustomException(NO_CHANGES_DETECTED);
        }

        Review updatedReview = reservation.getReview();
        updatedReview.setRating(updateReviewRequest.getRating());
        updatedReview.setContent(updateReviewRequest.getContent());

        return UpdateReviewDto.Response.from(updatedReview);
    }



}
