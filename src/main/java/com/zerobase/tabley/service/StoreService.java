package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Store;
import com.zerobase.tabley.dto.RegisterStoreDto;
import com.zerobase.tabley.dto.UpdateStoreDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.exception.ErrorCode;
import com.zerobase.tabley.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.zerobase.tabley.exception.ErrorCode.ALREADY_REGISTER_STORE_NAME;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    /**
     * 매장 상호는 중복을 허용하지 않는다고 가정
     */
    public RegisterStoreDto.Response addStore(RegisterStoreDto.Request request) {
        if (storeRepository.existsByStoreName(request.getStoreName())) {
            throw new CustomException(ALREADY_REGISTER_STORE_NAME);
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
    public void updateStore(String storeName, UpdateStoreDto.Request request) {
        Store store = storeRepository.findByStoreName(storeName)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        updateStoreInfo(request, store);
    }

    private static void updateStoreInfo(UpdateStoreDto.Request request, Store store) {
        if (request.getOwner() != null) store.setOwner(request.getOwner());
        if (request.getStoreName() != null) store.setStoreName(request.getStoreName());
        if (request.getStoreAddress() != null) store.setStoreAddress(request.getStoreAddress());
        if (request.getDescription() != null) store.setDescription(request.getDescription());
        if (request.getStorePhone() != null) store.setStorePhone(request.getStorePhone());
        if (request.getOpenAt() != null) store.setOpenAt(request.getOpenAt());
        if (request.getClosedAt() != null) store.setClosedAt(request.getClosedAt());
        if (request.getStoreCategory() != null) store.setStoreCategory(request.getStoreCategory());
    }


}
