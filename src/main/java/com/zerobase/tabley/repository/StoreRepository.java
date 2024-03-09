package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByUserId(String userId);
    void deleteStoreByStoreName(String storeName);
    boolean existsStoresByStoreName(String storeName);

    boolean existsStoresByUserId(String userId);

    Page<Store> findAll(Pageable pageable);
    Optional<Store> findByStoreName(String storeName);
}
