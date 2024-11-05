package com.sparta.outsourcing.domain.review.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.exception.customException.ReviewExceptions;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.entity.OrderStatus;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	public final ReviewRepository reviewRepository;
	public final OrderRepository orderRepository;
	public final MemberRepository memberRepository;

	public ReviewResponseDto createReview(ReviewRequestDto requestDto, Long memberId) {
		if (requestDto.getRating() < 1 || requestDto.getRating() > 5) {
			throw new ReviewExceptions(SCORE_OUT_OF_RANGE);
		}
		Order order	= orderRepository.findById(requestDto.getOrderId()).orElseThrow(
			() -> new ReviewExceptions(NOT_FOUND_ORDER)
		);
		Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
			() -> new ReviewExceptions(NOT_FOUND_USER)
		);
		if(!order.getStatus().equals(OrderStatus.COMPLETED)) {
			throw new ReviewExceptions(NOT_ORDER_STATUS_COMPLETED);
		}
		if(reviewRepository.findByOrderId(requestDto.getOrderId()).isPresent()) {
			throw new ReviewExceptions(REVIEW_ALREADY_EXISTS);
		}
		if(!order.getMember().getId().equals(memberId)) {
			throw new ReviewExceptions(HAS_NOT_PERMISSION);
		}
		Review newReview = Review.createOf(requestDto.getRating(), order);
		reviewRepository.save(newReview);
		return new ReviewResponseDto(newReview);
	}

	@Transactional
	public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, Long memberId) {
		if (requestDto.getRating() < 1 || requestDto.getRating() > 5) {
			throw new ReviewExceptions(SCORE_OUT_OF_RANGE);
		}
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new ReviewExceptions(NOT_FOUND_REVIEW)
		);
		if (!review.getOrder().getMember().getId().equals(memberId)) {
			throw new ReviewExceptions(HAS_NOT_PERMISSION);
		}
		review.update(requestDto.getRating());
		return new ReviewResponseDto(review);
	}

	@Transactional
	public void deleteReview(Long reviewId, Long memberId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new ReviewExceptions(NOT_FOUND_REVIEW));

		if(!review.getOrder().getMember().getId().equals(memberId)) {
			throw new ReviewExceptions(HAS_NOT_PERMISSION);
		}
		review.softDelete();
	}
}
