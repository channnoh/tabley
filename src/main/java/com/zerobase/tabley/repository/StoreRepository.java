package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByStoreName(String storeName);

    boolean existsByStoreName(String storeName);
}
