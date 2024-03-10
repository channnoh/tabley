package com.zerobase.tabley.controller;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.dto.ApproveReservationDTO;
import com.zerobase.tabley.dto.ConfirmVisitDto;
import com.zerobase.tabley.dto.MakeReservationDto;
import com.zerobase.tabley.dto.PartnerReservationDto;
import com.zerobase.tabley.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation("매장을 예약하는 API 입니다.")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{storeId}")
    public ResponseEntity<?> reserveStore(@PathVariable Long storeId,
                                          @RequestBody MakeReservationDto.Request reservationDto,
                                          @AuthenticationPrincipal Member user) {
        MakeReservationDto.Response reservedStore = reservationService.makeReservation(storeId, reservationDto, user);
        return ResponseEntity.ok(reservedStore);
    }

    @ApiOperation("매장 점주가 예약 정보를 승인/거절하는 API 입니다.")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/approve/{reservationId}")
    public ResponseEntity<?> approveReservation(@PathVariable Long reservationId,
                                                @RequestBody ApproveReservationDTO.Request request,
                                                @AuthenticationPrincipal Member partner) {

        ApproveReservationDTO.Response approveReservationDTO = reservationService.approveReservation(reservationId, request, partner);
        return ResponseEntity.ok(approveReservationDTO);
    }

    @ApiOperation("매장 점주가 헤당 날짜의 예약 정보 조회하는 API 입니다.")
    @PreAuthorize("hasRole('PARTNER')")
    @GetMapping("/list")
    public ResponseEntity<?> reservationListForPartner(@RequestParam(value = "dateTime") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                       @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                       @AuthenticationPrincipal Member partner) {

        Page<PartnerReservationDto.Response> reservationList = reservationService.reservationListForPartnerByDate(date, page, partner);
        return ResponseEntity.ok(reservationList);
    }

    @ApiOperation("예약자 방문확인 체크하는 API 입니다.")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/confirm/{reservationId}")
    public ResponseEntity<?> confirmVisit(@PathVariable Long reservationId,
                                            @RequestBody ConfirmVisitDto.Request request) {

        ConfirmVisitDto.Response visitConfirmMessage = reservationService.confirmVisit(reservationId, request);
        return ResponseEntity.ok(visitConfirmMessage);
    }

}
