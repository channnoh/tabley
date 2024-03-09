package com.zerobase.tabley.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;
}
