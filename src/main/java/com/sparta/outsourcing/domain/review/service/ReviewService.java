package com.sparta.outsourcing.domain.review.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.entity.OrderStatus;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.dto.ReviewStoreResponseDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	public final ReviewRepository reviewRepository;
	public final OrderRepository orderRepository;
	public final MemberRepository memberRepository;
	private final StoreRepository storeRepository;

	public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long memberId) {
		if (requestDto.getRating() < 1 || requestDto.getRating() > 5) {
			throw new IllegalArgumentException("점수는 1 에서 5 사이 숫자에서 골라주세요.");
		}
		Order order	= orderRepository.findById(requestDto.getOrderId()).orElseThrow(
			() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다.")
		);
		Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
			() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다.")
		);
		if(!order.getStatus().equals(OrderStatus.COMPLETED)) {
			throw new IllegalArgumentException("주문이 배달 완료 상태가 아닙니다.");
		}
		if(reviewRepository.findByOrderId(requestDto.getOrderId()).isPresent()) {
			throw new IllegalArgumentException("동일한 주문에 대해 이미 리뷰가 작성되었습니다.");
		}
		if(!order.getMember().getId().equals(memberId)) {
			throw new IllegalArgumentException("요청된 주문은 해당 멤버의 주문이 아닙니다.");
		}
		Review newReview = Review.createOf(requestDto.getRating());
		return new ReviewResponseDto(newReview);
	}

	public Page<ReviewStoreResponseDto> getReviewByStoreId(Long storeId, int page, int size, int minRating, int maxRating) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다.")
		);
		if(minRating < 1 || maxRating > 5 || minRating > maxRating) {
			throw new IllegalArgumentException("별점 범위는 1~5 사이이고, 최소 별점은 최대 별점보다 작을 수 없습니다.");
		}
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		Page<Review> reviews = reviewRepository.findByStoreAndRatingBetween(store, minRating, maxRating, pageable);
		return reviews.map(this::toReviewStoreResponseDto);
	}

	// ============== 편의 메서드 ==============

	private ReviewStoreResponseDto toReviewStoreResponseDto(Review review) {
		return new ReviewStoreResponseDto(review.getOrder().getId(), review.getRating());
	}
}
