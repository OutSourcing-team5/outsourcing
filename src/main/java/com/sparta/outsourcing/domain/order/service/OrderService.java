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

		if (store.isDeleted()) {
			throw new IllegalArgumentException("폐업한 가게입니다.");
		}

		Time currentTime = Time.valueOf(LocalTime.now()); //Time 현재시간 확인
		if (currentTime.before(store.getOpenTime()) || currentTime.after(store.getCloseTime())) {
			throw new IllegalArgumentException("주문 가능 시간이 아닙니다.");
		}

		Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow(
			() -> new IllegalArgumentException("해당 메뉴를 찾을 수 없습니다.")
		);

		if (menu.isDeleted()) {
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
		// 주문 상태 전환 유효성 검사 -> 기본값이 pending
		String currentStatus = order.getStatus().name();
		String requestedStatus = statusRequestDto.getOrderStatus();

		if ("PENDING".equals(currentStatus) && !"ACCEPTED".equals(requestedStatus) && !"REJECTED".equals(requestedStatus)) {
			throw new IllegalArgumentException("대기중인 주문만 거절 가능합니다.");
		}

		if ("COMPLETED".equals(currentStatus) && !"COMPLETED".equals(requestedStatus)) {
			throw new IllegalArgumentException("수락된 주문만 완료 가능합니다.");
		}

		if ("ACCEPTED".equals(currentStatus) && !"COMPLETED".equals(requestedStatus)) {
			throw new IllegalArgumentException("대기중인 주문만 수락 가능합니다.");
		}

		// 상태 업데이트
		order.setStatus(OrderStatus.valueOf(requestedStatus));
		orderRepository.save(order);

		// 업데이트된 주문 정보를 포함한 응답 반환
		return new OrderResponseDto(order);


	}
}
