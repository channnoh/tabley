package com.zerobase.tabley.controller;

import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.dto.StoreInfoDto;
import com.zerobase.tabley.dto.UpdateStoreDto;
import com.zerobase.tabley.service.StoreService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @ApiOperation("매장등록 API 입니다.")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/register")
    public ResponseEntity<?> registerStore(@RequestBody @Valid RegisterStoreDto.Request request) {
        RegisterStoreDto.Response savedStore = storeService.addStore(request);

        return ResponseEntity.ok(savedStore);
    }

    @ApiOperation("매장정보 수정 API 입니다.")
    @PreAuthorize("hasRole('PARTNER')")
    @PostMapping("/{userId}")
    public String updateStore(@PathVariable String userId,
                              @RequestBody UpdateStoreDto.Request request) {
        storeService.updateStore(userId, request);

        return "매장정보 수정 완료";
    }

    @ApiOperation("매장삭제 API 입니다.")
    @PreAuthorize("hasRole('PARTNER')")
    @DeleteMapping("/{storeName}")
    public String deleteStore(@PathVariable String storeName) {
        storeService.deleteStore(storeName);

        return "매장삭제 완료";
    }

    /**
     *  가정렬 기준에 따른 매장 목록 조회
     */

    @ApiOperation("정렬 기준에 맞게 매장 목록을 조회하는 API 입니다.")
    @GetMapping("/list")
    public ResponseEntity<?> getStoreList(@RequestParam(value = "page") Integer page, String criteria) {
        Page<StoreInfoDto> storeList = storeService.getStorePage(page, criteria);
        return ResponseEntity.ok(storeList);
    }


    /**
     *  매장 상세 정보 조회
     */

    @ApiOperation("매장 상세 정보 조회 API 입니다.")
    @GetMapping("/info/{storeName}")
    public ResponseEntity<?> getStoreInfo(@PathVariable String storeName) {
        StoreInfoDto storeInfo = storeService.getStoreInfo(storeName);
        return ResponseEntity.ok(storeInfo);
    }


}
