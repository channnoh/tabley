package com.zerobase.tabley.controller;

import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.dto.UpdateStoreDto;
import com.zerobase.tabley.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * @ApiOperation: swagger description
     * @PreAuthorize: 메서드 실행하기 전에 권한 검사
     */

    @ApiOperation("매장등록")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid RegisterStoreDto.Request request) {
        RegisterStoreDto.Response savedStore = storeService.addStore(request);

        return ResponseEntity.ok(savedStore);
    }

    @ApiOperation("매장정보 수정")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/{userId}")
    public String updateStore(@PathVariable String userId,
                              @RequestBody @Valid UpdateStoreDto.Request request) {
        storeService.updateStore(userId, request);

        return "매장등록 완료";
    }

    @ApiOperation("매장삭제")
    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/{userId}")
    public String deleteStore(@PathVariable String userId) {
        storeService.deleteStore(userId);

        return "매장삭제 완료";
    }


}
