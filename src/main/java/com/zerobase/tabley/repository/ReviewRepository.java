package com.zerobase.tabley.repository;

import com.zerobase.tabley.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long id);

    void deleteReviewById(Long id);
}
