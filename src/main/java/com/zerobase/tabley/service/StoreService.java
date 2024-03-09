package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.dto.StoreInfoDto;
import com.zerobase.tabley.dto.UpdateStoreDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.zerobase.tabley.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    /**
     * 파트너 한명 당 매장 하나 등록가능
     * 매장 상호는 중복을 허용하지 않는다고 가정
     */
    public RegisterStoreDto.Response addStore(RegisterStoreDto.Request request) {
        if(storeRepository.existsStoresByUserId(request.getUserId())){
            throw new CustomException(ALREADY_REGISTER_STORE_USER);
        }
        if (storeRepository.existsStoresByStoreName(request.getStoreName())) {
            throw new CustomException(ALREADY_EXISTS_STORE);
        }
        Store savedStore = storeRepository.save(request.toEntity());
        return RegisterStoreDto.Response.fromEntity(savedStore);
    }

    /**
     * JPA DB 업데이트 예
     * EntityManager를 통해 트랜잭션 내에서 영속성 컨텍스트 -> 변경 필드 db 업데이트
     EntityManager em = entityManagerFactory.createEntityManager();
     EntityTransaction tx = em.getTransaction();
     tx.begin(); //트랜잭션 시작
     Store store = em.find(Store.class, id);
     store.chanegeStoreName(storeName); // 엔티티만 변경
     tx.commit(); //트랜잭션 커밋
     **/

    /**
     * Spring Data Jpa를 활용하여 transactional로 처리
     * Dirty checking을 통해 변경된 값 업데이트
     * save 호출이 필요 없음, 변경 감지로 인해 자동으로 업데이트
     * null 처리를 더 깔끔하게 할 수 있을 거 같은데 공부해서 리팩토링 할 것
     * => AOP를 활용해볼 수 있지 않을까 고민
     */
    @Transactional
    public void updateStore(String userId, UpdateStoreDto.Request request) {
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        updateStoreInfo(request, store);
    }

    /**
     * Update field null 체크
     */
    private static void updateStoreInfo(UpdateStoreDto.Request request, Store store) {
        if (request.getOwner() != null) store.setOwner(request.getOwner());
        if (request.getStoreName() != null) store.setStoreName(request.getStoreName());
        if (request.getStoreAddress() != null) store.setStoreAddress(request.getStoreAddress());
        if (request.getDescription() != null) store.setDescription(request.getDescription());
        if (request.getContact() != null) store.setContact(request.getContact());
        if (request.getOpenAt() != null) store.setOpenAt(request.getOpenAt());
        if (request.getClosedAt() != null) store.setClosedAt(request.getClosedAt());
        if (request.getLat() != null) store.setLat(request.getLat());
        if (request.getLnt() != null) store.setLnt(request.getLnt());
        if (request.getStoreCategory() != null) store.setStoreCategory(request.getStoreCategory());
    }

    @Transactional
    public void deleteStore(String storeName) {
        if (!storeRepository.existsStoresByStoreName(storeName)) {
            throw new CustomException(STORE_NOT_FOUND);
        }
        storeRepository.deleteStoreByStoreName(storeName);
    }

    /**
     * Pageable parameter로 받아와서 정렬 기준에 따라 JPA paging 처리
     * default는 매장이름순
     */

    public Page<StoreInfoDto> getStorePage(Pageable pageable) {
        Page<Store> page = storeRepository.findAll(pageable);
        return StoreInfoDto.toDto(page);
    }


    /**
     * 사용자가 매장 상세 정보 조회 가능한 서비스 메서드
     */

    public StoreInfoDto getStoreInfo(String storeName) {
        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        return StoreInfoDto.fromEntity(store);
    }

}
