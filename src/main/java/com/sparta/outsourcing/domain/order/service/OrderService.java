package com.sparta.outsourcing.domain.order.service;

import java.sql.Time;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.entity.OrderStatus;
import com.sparta.outsourcing.domain.order.dto.OrderStatusRequestDto;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final MenuRepository menuRepository;
	private final StoreRepository storeRepository;

	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 유저가 없습니다.")
		);

		Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
			() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다.")
		);

		if (store.isDelete()) {
			throw new IllegalArgumentException("폐업한 가게입니다.");
		}

		Time currentTime = Time.valueOf(LocalTime.now()); //Time 현재시간 확인
		if (currentTime.before(store.getOpenTime()) || currentTime.after(store.getCloseTime())) {
			throw new IllegalArgumentException("주문 가능 시간이 아닙니다.");
		}

		Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow(
			() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다.")
		);

		if (menu.isDelete()) {
			throw new IllegalArgumentException("선택한 메뉴는 현재 주문할 수 없습니다.");
		}

		if (menu.getPrice() < store.getMinPrice()) {
			throw new IllegalArgumentException("최소 주문 금액을 충족하지 않습니다.");
		}

		Order order = Order.createOf(member, store, menu);
		orderRepository.save(order);

		return new OrderResponseDto(order);
	}
	@Transactional
	public OrderResponseDto updateOrderStatus(OrderStatusRequestDto statusRequestDto, Long memberId) {
		Order order = orderRepository.findById(statusRequestDto.getOrderId()).orElseThrow(
			()-> new IllegalArgumentException("해당 주문을 찾을 수 없습니다.")
		);
		// 권한 확인
		if (!order.getMember().getId().equals(memberId)) {
			throw new IllegalArgumentException("권한이 없습니다.");
		}

		// 주문 상태 유효성 검사
		String currentStatus = order.getStatus().name();
		String requestedStatus = statusRequestDto.getOrderStatus();

		// 대기 상태로 바꾸는 요청: 바로 예외처리
		if (requestedStatus.equals("PENDING")) {
			throw new IllegalArgumentException("대기 상태로 변경할 수 없습니다.");
		}

		// 수락 상태로 바꾸는 요청: 대기가 아니면 언제나 예외처리
		if (requestedStatus.equals("ACCEPTED") && !currentStatus.equals("PENDING")) {
			throw new IllegalArgumentException("대기 상태가 아니면 수락 상태로 변경할 수 없습니다.");
		}

		// 거절 상태로 바꾸는 요청: 대기가 아니면 언제나 예외처리
		if (requestedStatus.equals("REJECTED") && currentStatus.equals("PENDING")) {
			throw new IllegalArgumentException("대기 상태가 아니면 거절 상태로 변경할 수 없습니다.");
		}

		//완료 상태로 바꾸는 요청: 수락이 아니면 언제나 예외처리
		if (requestedStatus.equals("COMPLETED") && currentStatus.equals("ACCEPTED")) {
			throw new IllegalArgumentException("수락 상태가 아니면 완료 상태로 변경할 수 없습니다.");
		}
		// 상태 업데이트
		order.setStatus(OrderStatus.valueOf(requestedStatus));
		orderRepository.save(order);

		// 업데이트된 주문 정보를 포함한 응답 반환
		return new OrderResponseDto(order);
	}

	public void deleteOrder(Long orderId, Long memberId) {
		Order order = orderRepository.findById(orderId).orElseThrow(()
			->new IllegalArgumentException("해당 주문을 찾을 수 없습니다. "));

		// 권한 확인 : 주문한 사용자만 취소할 수 있도록 설정
		if (!order.getMember().getId().equals(memberId)) {
			throw new IllegalArgumentException(" 해당 주문을 취소할 권한이 없습니다.");
		}

		// 주문 상태가 완료되었는지 확인하고 완료된 주문은 취소 불가능하게 설정
		if(order.getStatus() == OrderStatus.COMPLETED) {
			throw new IllegalArgumentException("완료된 주문은 취소할 수 없습니다.");
		}
		//소프트 삭제 수행
		order.delete();
	}
}
