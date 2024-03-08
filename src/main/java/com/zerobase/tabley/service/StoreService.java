package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    public RegisterStoreDto.Response addStore(RegisterStoreDto.Request request) {
        Store savedStore = storeRepository.save(request.toEntity());
        return RegisterStoreDto.Response.fromEntity(savedStore);


    }



}
