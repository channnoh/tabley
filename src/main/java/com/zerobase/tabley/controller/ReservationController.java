package com.zerobase.tabley.controller;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.dto.ReservationDto;
import com.zerobase.tabley.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation("매장 예약")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{storeId}")
    public ResponseEntity<?> reserveStore(@PathVariable Long storeId,
                                          @RequestBody ReservationDto.Request reservationDto,
                                          @AuthenticationPrincipal Member member) {
        ReservationDto.Response reservedStore = reservationService.makeReservation(storeId, reservationDto, member);
        return ResponseEntity.ok(reservedStore);
    }


}
