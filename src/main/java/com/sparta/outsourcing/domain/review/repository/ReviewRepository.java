package com.sparta.outsourcing.domain.review.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.store.entity.Store;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	Optional<Object> findByOrderId(Long orderId);

	Page<Review> findAllByStoreAndDeleteFalse(Store store, Pageable reviewPageable);
}

