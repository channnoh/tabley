package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.type.StoreCategory;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoDto {

    private String storeName;
    private String storeAddress;
    private String description;
    private String storePhone;
    private LocalTime openAt;
    private LocalTime closedAt;
    private String owner;
    private StoreCategory storeCategory;
    private Double rating;

    public static StoreInfoDto fromEntity(Store store) {
        return StoreInfoDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .description(store.getDescription())
                .storePhone(store.getStorePhone())
                .openAt(store.getOpenAt())
                .closedAt(store.getClosedAt())
                .owner(store.getOwner())
                .storeCategory(store.getStoreCategory())
                .rating(store.getRating())
                .build();
    }

    public static Page<StoreInfoDto> toDto(Page<Store> page) {
        return page.map(store -> StoreInfoDto.builder()
                .storeName(store.getStoreName())
                .storeAddress(store.getStoreAddress())
                .description(store.getDescription())
                .storePhone(store.getStorePhone())
                .openAt(store.getOpenAt())
                .closedAt(store.getClosedAt())
                .owner(store.getOwner())
                .storeCategory(store.getStoreCategory())
                .rating(store.getRating())
                .build());

    }
}
