package com.sparta.outsourcing.domain.review.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.entity.OrderStatus;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewDeleteRequestDto;
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
		Review newReview = Review.createOf(requestDto.getRating(), order);
		reviewRepository.save(newReview);
		return new ReviewResponseDto(newReview);
	}

	@Transactional
	public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateRequestDto requestDto, Long memberId) {
		if (requestDto.getRating() < 1 || requestDto.getRating() > 5) {
			throw new IllegalArgumentException("점수는 1 에서 5 사이 숫자에서 골라주세요.");
		}
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다.")
		);
		if (!review.getOrder().getMember().getId().equals(memberId)) {
			throw new IllegalArgumentException("리뷰를 수정할 권한이 없습니다.");
		}
		review.update(requestDto.getRating());
		return new ReviewResponseDto(review);
	}

	@Transactional
	public void deleteReview(Long reviewId, Long memberId) {
		Review review = reviewRepository.findById(reviewId).orElseThrow(
			() -> new IllegalArgumentException("해당 리뷰가 존재하지 않습니다."));

		if(!review.getOrder().getMember().getId().equals(memberId)) {
			throw new IllegalArgumentException("리뷰 삭제 권한이 없습니다.");
		}
		review.softDelete();
	}
}
