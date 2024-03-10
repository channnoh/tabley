package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Reservation;
import com.zerobase.tabley.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByIdAndReservationDate(Long storeId, LocalDateTime reservationTime);

    Optional<Reservation> findById(Long id);

    Page<Reservation> findByStoreAndReservationDateBetweenOrderByReservationDateAsc(Store store,
                                                                                    LocalDateTime startTime,
                                                                                    LocalDateTime endTime,
                                                                                    Pageable pageRequest);

}
