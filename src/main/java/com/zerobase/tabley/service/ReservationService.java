package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.dto.ApproveReservationDTO;
import com.zerobase.tabley.dto.ConfirmVisitDto;
import com.zerobase.tabley.dto.MakeReservationDto;
import com.zerobase.tabley.dto.PartnerReservationDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.repository.ReservationRepository;
import com.zerobase.tabley.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.zerobase.tabley.exception.ErrorCode.*;
import static com.zerobase.tabley.type.ReservationStatus.APPROVAL;
import static com.zerobase.tabley.type.ReservationStatus.VISIT_CONFIRMED;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;


    /**
     * 예약 가능 여부 확인 및 예약 진행
     * 예약 중복 방지를 위해 @Transactional 사용
     * 요청 시간에 이미 예약이 차있으면 예외처리
     * 예약 초기 상태는 WAITING(예약 대기) 상태
     */
    @Transactional
    public MakeReservationDto.Response makeReservation(Long storeId, MakeReservationDto.Request request, Member member) {

        if (!isPossibleTime(request.getReservationDate())) {
            throw new CustomException(WRONG_RESERVATION_DATETIME);
        }

        if (!isReservationFull(storeId, request.getReservationDate())) {
            throw new CustomException(RESERVATION_ALREADY_FULL);
        }

        Store store = getStore(storeId);
        Reservation reservation = MakeReservationDto.Request.toEntity(request, store, member);

        Reservation completedReservation = reservationRepository.save(reservation);
        return MakeReservationDto.Response.fromEntity(completedReservation);
    }

    private Store getStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
    }

    /**
     * 동일한 매장에서, 주어진 예약 시간에 다른 예약이 있는지,
     * 예약하려는 날짜와 시간이 예약하려는 시점보다 한 시간 이후인지 확인(예약은 최소 1시간 전에 진행해야된다는 정책)
     */

    private boolean isReservationFull(Long storeId, LocalDateTime reservationTime) {
        List<Reservation> existingReservations = reservationRepository.findByIdAndReservationDate(storeId, reservationTime);

        return existingReservations.isEmpty();
    }

    private boolean isPossibleTime(LocalDateTime reservationTime) {
        LocalDateTime minimumReservationTime = LocalDateTime.now().plusHours(1);
        return reservationTime.isAfter(minimumReservationTime);
    }

    /**
     * 해당 예약날짜의 예약 내역 리스트를 paging 처리해서 반환
     */
    public Page<PartnerReservationDto.Response> reservationListForPartnerByDate(LocalDate date, Integer page, Member member) {

        Store store = storeRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        Page<Reservation> reservations =
                reservationRepository.findByStoreAndReservationDateBetweenOrderByReservationDateAsc(
                        store,
                        LocalDateTime.of(date, LocalTime.MIN),
                        LocalDateTime.of(date, LocalTime.MAX),
                        PageRequest.of(page, 10)
                );

        if (reservations.getSize() == 0) {
            throw new CustomException(RESERVATION_NOT_FOUND_FOR_DATE);
        }
        return reservations.map(PartnerReservationDto.Response::fromEntity);
    }


    /**
     * 매장 점주(Partner) 매장의 예약 내역 확인 후 예약 승인/거절
     * 로그인 유저와 예약상태를 결정하고자 하는 매장의 점주가 같은지를 검증할 떄
     * userid로 검증하고 있는데 member entity를 partner, user로 나누고
     * partner와 store를 OneToOne mapping 하는 것이 좋을 거 같은데 고민 해볼 것
     * dirty checking 으로 따로 save 없이 예약상태 변경(entity가 영속상태여야 하고 transaction으로 묶여 있어야함)
     */

    @Transactional
    public ApproveReservationDTO.Response approveReservation(Long reservationId, ApproveReservationDTO.Request approveReservationDTO, Member partner) {

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        if (!reservation.getStore().getUserId().equals(partner.getUserId())) {
            throw new CustomException(ACCESS_DENIED_RESERVATION_APPROVAL);
        }


        if (approveReservationDTO.getReservationStatus() != null) {
            reservation.setReservationStatus(approveReservationDTO.getReservationStatus());
        } else {
            throw new NullPointerException("예약 상태를 결정해주세요");
        }

        return ApproveReservationDTO.Response.fromEntity(reservation);
    }

    @Transactional
    public ConfirmVisitDto.Response confirmVisit(Long reservationId, ConfirmVisitDto.Request confirmVisitRequest) {

        Reservation reservation = validateVisitConfirmation(reservationId, confirmVisitRequest);
        reservation.setReservationStatus(VISIT_CONFIRMED);

        return ConfirmVisitDto.Response.fromEntity(reservation.getStore().getStoreName());
    }

    private Reservation validateVisitConfirmation(Long reservationId, ConfirmVisitDto.Request confirmVisitRequest) {

        // 존재하는 예약내역인지 검증
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 입력된 핸드폰 번호와 예약자의 핸드폰 번호가 일치하는지 검증
        if (!reservation.getMember().getPhoneNumber().equals(confirmVisitRequest.getPhoneNumber())) {
            throw new CustomException(WRONG_RESERVATION_USER_INFORMATION);
        }

        // 이미 방문 확인된 예약인지 검증
        if (reservation.getReservationStatus().equals(VISIT_CONFIRMED)) {
            throw new CustomException(ALREADY_CONFIRMED_RESERVATION);
        }

        // 예약상태가 승인 상태인지 검증
        if (!reservation.getReservationStatus().equals(APPROVAL)) {
            throw new CustomException(UNAPPROVED_RESERVATION_BY_OWNER);
        }

        // 예약시간 10분 전에 도착했는지 검증
        if (!confirmVisitRequest.getArrivalTime().isBefore(reservation.getReservationDate().minusMinutes(10))) {
            throw new CustomException(RESERVATION_CHECK_IN_TIME_EXPIRED);
        }

        return reservation;
    }

}
