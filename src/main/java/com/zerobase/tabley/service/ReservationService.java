package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.dto.ReservationDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.repository.MemberRepository;
import com.zerobase.tabley.repository.ReservationRepository;
import com.zerobase.tabley.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.zerobase.tabley.exception.ErrorCode.RESERVATION_ALREADY_FULL;
import static com.zerobase.tabley.exception.ErrorCode.STORE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;


    /**
     * 예약 가능 여부 확인 및 예약 진행
     * 예약 중복 방지를 위해 @Transactional 사용
     * 요청 시간에 이미 예약이 차있으면 예외처리
     * 예약 초기 상태는
     */
    @Transactional
    public ReservationDto.Response makeReservation(Long storeId, ReservationDto.Request request, Member member) {

        if (!isReservationTimeAvailable(storeId, request.getReservationDate())) {
            throw new CustomException(RESERVATION_ALREADY_FULL);
        }

        Store store = getStore(storeId);
        Reservation reservation = ReservationDto.Request.toEntity(request, store, member);

        Reservation completedReservation = reservationRepository.save(reservation);
        return ReservationDto.Response.fromEntity(completedReservation);
    }

    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }

    /**
     * 동일한 매장에서, 주어진 예약 시간에 다른 예약이 있는지 확인
     */

    private boolean isReservationTimeAvailable(Long storeId, LocalDateTime reservationTime) {
        List<Reservation> existingReservations = reservationRepository.findByIdAndReservationDate(storeId, reservationTime);
        return existingReservations.isEmpty();
    }


}
