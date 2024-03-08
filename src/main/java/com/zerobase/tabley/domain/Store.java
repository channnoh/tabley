package com.zerobase.tabley.domain;

import com.zerobase.tabley.type.StoreCategory;
import lombok.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String userId;

    private String owner;
    private String storeName;
    private String storeAddress;
    private String description;
    private String storePhone;
    private LocalTime openAt;
    private LocalTime closedAt;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    @Builder.Default
    private Double rating = 0.0;


    /**
     * mapping 미완료
     */
//    @OneToMany
//    private List<Reservation> reservationList;
//
//    @OneToMany
//    private List<Review> reviews;


}
