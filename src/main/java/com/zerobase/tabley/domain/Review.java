package com.zerobase.tabley.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    private Double rating;
    private String content;

    @ManyToOne
    @JoinColumn(name = "STORE_ID")
    private Store store;

    @OneToOne(mappedBy = "review")
    private Reservation reservation;


}
