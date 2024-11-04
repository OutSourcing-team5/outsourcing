package com.sparta.outsourcing.domain.review.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Page<Review> findAllByStoreContaining(Store store, Pageable reviewPageable);
	Optional<Object> findByOrderId(Long orderId);
}
