package com.zerobase.tabley.controller;

import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @ApiOperation("상점등록")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid RegisterStoreDto.Request request) {
        RegisterStoreDto.Response savedStore = storeService.addStore(request);

        log.info("store register -> {}", request.getStoreName());
        return ResponseEntity.ok(savedStore);
    }


}
