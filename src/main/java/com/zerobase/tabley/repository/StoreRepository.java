package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUserId(String userId);

    void deleteStoreByUserId(String userId);
    boolean existsStoresByUserId(String userId);

}
