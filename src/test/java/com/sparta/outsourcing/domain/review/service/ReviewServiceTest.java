package com.sparta.outsourcing.domain.review.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Time;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.outsourcing.common.exception.customException.ReviewExceptions;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.entity.OrderStatus;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.entity.Store;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@InjectMocks
	private ReviewService reviewService;
	@Mock
	private ReviewRepository reviewRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private MemberRepository memberRepository;
	private Order order;
	private Member member;
	private Store store;
	private Menu menu;

	@BeforeEach
	void setUp() {
		//리뷰를 달 멤버생성
		member = Member.createOf("testUser", "1234Qwer!", "test@example.com", "Seoul", MemberRole.USER);
		store = Store.createOf("testStore", Time.valueOf("09:00:00"), Time.valueOf("21:00:00"),100,member);
		menu = Menu.createOf("testMenu", 10000, store);
		order = Order.createOf(member, store, menu, 1);
		ReflectionTestUtils.setField(member, "id", 1L);
		ReflectionTestUtils.setField(order,"id", 1L);
	}

	@Test
	@DisplayName("리뷰가 정상적으로 작성되는지 테스트")
	void createReviewTest() {
		//given
		Long memberId = member.getId();
		Long orderId = order.getId();
		ReviewRequestDto reviewRequestDto = new ReviewRequestDto();
		ReflectionTestUtils.setField(reviewRequestDto, "memberId", memberId);
		ReflectionTestUtils.setField(reviewRequestDto, "orderId", orderId);
		ReflectionTestUtils.setField(reviewRequestDto, "rating", 4);
		ReflectionTestUtils.setField(order,"status", OrderStatus.COMPLETED);
		//Order member 존재하는 하고 이미 작성된 리뷰가 없는경우
		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
		when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
		when(reviewRepository.findByOrderId(1L)).thenReturn(Optional.empty());
		//when
		ReviewResponseDto reviewResponseDto = reviewService.createReview(reviewRequestDto, 1L);
		//then
		assertNotNull(reviewResponseDto); //null 이 아니어야됨
		assertEquals(4, reviewResponseDto.getRating()); // 지정한 4점이어야됨
		assertEquals(orderId, reviewResponseDto.getOrderId()); //주문아이디가 입력한 값과 같아야됨
	}

	@Test
	@DisplayName("권한확인 - 리뷰 수정 실패 테스트")
	void updateReviewFailTest_Unauthorized() {
		//given
		Long reviewId = 1L;
		Long requestMemberId = 2L; // 수정을 요청하는 사람의 memberId

		Review existingReview = Review.createOf(4, order);
		ReflectionTestUtils.setField(existingReview, "id", reviewId);
		// repository 에서 기존 리뷰가 있는지 확인
		when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(existingReview));
		ReviewUpdateRequestDto updateRequestDto = new ReviewUpdateRequestDto(5);

		//when, then
		ReviewExceptions exceptions = assertThrows(
			ReviewExceptions.class,
			() -> reviewService.updateReview(reviewId, updateRequestDto, requestMemberId)
		);

		//검증
		assertEquals(HAS_NOT_PERMISSION, exceptions.getExceptionCode());
	}
}