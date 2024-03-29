package com.zerobase.tabley.dto;

import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.type.StoreCategory;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

public class RegisterStoreDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        @NotBlank(message = "ID is required")
        private String userId;

        @NotBlank(message = "owner is required")
        private String owner;

        @NotBlank(message = "storeName is required")
        private String storeName;

        @NotBlank(message = "storeAddress is required")
        private String storeAddress;

        @NotBlank(message = "description is required")
        private String description;

        @NotBlank(message = "Store number is required")
        private String contact;

        @NotNull(message = "open time is required")
        private LocalTime openAt;

        @NotNull(message = "closed time is required")
        private LocalTime closedAt;

        @Builder.Default
        private Double lat = 0.0;

        @Builder.Default
        private Double lnt = 0.0;

        @NotNull
        private StoreCategory storeCategory;

        public Store toEntity() {
            return Store.builder()
                    .userId(userId)
                    .owner(owner)
                    .storeName(storeName)
                    .storeAddress(storeAddress)
                    .description(description)
                    .contact(contact)
                    .openAt(openAt)
                    .closedAt(closedAt)
                    .storeCategory(storeCategory)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String storeName;
        private String storeAddress;
        private String description;
        private String contact;
        private String owner;
        private LocalTime openAt;
        private LocalTime closedAt;
        private StoreCategory storeCategory;

        public static Response fromEntity(Store store) {
            return Response.builder()
                    .storeName(store.getStoreName())
                    .storeAddress(store.getStoreAddress())
                    .description(store.getDescription())
                    .contact(store.getContact())
                    .owner(store.getOwner())
                    .openAt(store.getOpenAt())
                    .closedAt(store.getClosedAt())
                    .storeCategory(store.getStoreCategory())
                    .build();
        }
    }
}
