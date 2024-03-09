package com.zerobase.tabley.dto;

import com.zerobase.tabley.type.StoreCategory;
import lombok.*;

import java.time.LocalTime;

public class UpdateStoreDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private String owner;
        private String storeName;
        private String storeAddress;
        private String description;
        private String contact;
        private LocalTime openAt;
        private LocalTime closedAt;
        private StoreCategory storeCategory;
        private Double lat = 0.0;
        private Double lnt = 0.0;

    }

}
