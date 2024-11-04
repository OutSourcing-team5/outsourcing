package com.sparta.outsourcing.domain.review.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.store.entity.Store;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findAllByStoreContaining(Store store, Pageable reviewPageable);
	Optional<Object> findByOrderId(Long orderId);

	Page<Review> findByStoreAndRatingBetween(Store store, int minRating, int maxRating, Pageable pageable);
}
