package com.zerobase.tabley.dto;

import com.zerobase.tabley.type.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
        private String storePhone;
        private LocalTime openAt;
        private LocalTime closedAt;
        private StoreCategory storeCategory;

    }

}
