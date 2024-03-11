package com.zerobase.tabley.domain;

import com.zerobase.tabley.type.StoreCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    private String owner;
    private String storeName;
    private String storeAddress;

    /**
     * 매장 설명은 길이 제한을 두지 않기 위해서 @Lob 어노테이션 붙여서
     * CLOB으로 매핑(문자열)
     * */
    @Lob
    private String description;

    private String contact;
    private LocalTime openAt;
    private LocalTime closedAt;
    private Double lat;
    private Double lnt;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    @Builder.Default
    private Double rating = 0.0;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservations = new ArrayList<>();


    @OneToMany(mappedBy = "store")
    private List<Review> reviews = new ArrayList<>();

    /**
     * 리뷰 작성 되면 매장의 rating update
     */
    public void updateRating() {
        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        int totalCount = reviews.size();

        this.rating = (double) Math.round(totalRating / totalCount * 10) / 10;

    }
}
