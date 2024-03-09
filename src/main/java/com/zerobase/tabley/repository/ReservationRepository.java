package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByIdAndReservationDate(Long storeId, LocalDateTime reservationTime);


}
