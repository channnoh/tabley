package com.zerobase.tabley.domain;

import com.zerobase.tabley.type.ReservationStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Long id;

    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus reservationStatus = ReservationStatus.WAITING;

    private boolean isVisited;

    /**
     * JPA 연관관계 매핑
     * 연관관계 주인은 외래키를 가진 쪽(Many 쪽) -> 외래키를 관리
     * 고객(1) -> 예약(*): 고객은 여러개의 예약 가능
     * 매장(1) -> 예약(*): 매장은 여러 예약 받을 수 있음
     * @JoinColumn name 값에 외래키 조인하려는 테이블 외래키 넣어줌
     */

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;


}
