package com.sparta.outsourcing.domain.order.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.common.exception.customException.OrderExceptions;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.entity.Option;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.menu.repository.OptionRepository;
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
	private final OptionRepository optionRepository;

	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new OrderExceptions(NOT_FOUND_USER)
		);

		Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
			() -> new OrderExceptions(NOT_FOUND_STORE)
		);

		if (store.isInactive()) {
			throw new OrderExceptions(STORE_OUT_OF_BUSINESS);
		}

		Time currentTime = Time.valueOf(LocalTime.now()); //Time 현재시간 확인
		if (currentTime.before(store.getOpenTime()) || currentTime.after(store.getCloseTime())) {
			throw new OrderExceptions(STORE_CLOSED);
		}

		if(!store.isOpen()){
			throw new OrderExceptions(STORE_CLOSED_BY_OWER);
		}

		Menu menu = menuRepository.findById(requestDto.getMenuId()).orElseThrow(
			() -> new OrderExceptions(NOT_FOUND_MENU)
		);

		if (menu.isInactive()) {
			throw new OrderExceptions(NOT_ORDER_NOW);
		}

		for (int i = 0; i < requestDto.getOptionIds().size(); i++) {
			Long optionId = requestDto.getOptionIds().get(i);  // 인덱스를 사용하여 optionId 접근
			Option option = optionRepository.findById(optionId).orElseThrow(
				() -> new OrderExceptions(NOT_FOUND_OPTION)
			);
		}

		List<Option> options = new ArrayList<>();
		int totalOptionPrice = 0;
		if (requestDto.getOptionIds() != null && !requestDto.getOptionIds().isEmpty()) {
			for (int i = 0; i < requestDto.getOptionIds().size(); i++) {
				Long optionId = requestDto.getOptionIds().get(i);  // 인덱스를 사용하여 optionId 접근
				Option option = optionRepository.findById(optionId).orElseThrow(
					() -> new OrderExceptions(NOT_FOUND_OPTION)
				);

				if (!menu.getMenuOptions().stream().anyMatch(menuOption -> menuOption.getOption().equals(option))) {
					throw new OrderExceptions(OPTION_NOT_BELONG_TO_MENU);
				}

				totalOptionPrice += option.getOptionPrice();
			}
		}

		//주문 금액보다 적을 시
		if (member.getPoints() < (menu.getPrice()+totalOptionPrice)*requestDto.getCount() ) {
			throw new OrderExceptions(INSUFFICIENT_POINTS);
		}

		if (menu.getPrice() < store.getMinPrice()) {
			throw new OrderExceptions(LOWER_THAN_MIN_ORDER);
		}

		Order order = Order.createOf(member, store, menu, requestDto.getCount(), totalOptionPrice);
		orderRepository.save(order);

		return new OrderResponseDto(order);
	}
	@Transactional
	public OrderResponseDto updateOrderStatus(OrderStatusRequestDto statusRequestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new OrderExceptions(NOT_FOUND_USER)
		);
		Order order = orderRepository.findById(statusRequestDto.getOrderId()).orElseThrow(
			()-> new OrderExceptions(NOT_FOUND_MENU)
		);
		// 권한 확인
		if (!order.getStore().getMember().getId().equals(memberId)) {
			throw new OrderExceptions(HAS_NOT_PERMISSION);
		}

		// 주문 상태 유효성 검사
		String currentStatus = order.getStatus().name();
		String requestedStatus = statusRequestDto.getOrderStatus();

		// 대기 상태로 바꾸는 요청: 바로 예외처리
		if (requestedStatus.equals("PENDING")) {
			throw new OrderExceptions(CANNOT_CHANGE_TO_PENDING);
		}

		// 수락 상태로 바꾸는 요청: 대기가 아니면 언제나 예외처리
		if (requestedStatus.equals("ACCEPTED") && !currentStatus.equals("PENDING")) {
			throw new OrderExceptions(ACCEPT_ONLY_PENDING);
		}

		// 거절 상태로 바꾸는 요청: 대기가 아니면 언제나 예외처리
		if (requestedStatus.equals("REJECTED") && !currentStatus.equals("PENDING")) {
			throw new OrderExceptions(REJECTED_ONLY_PENDING);
		}

		//완료 상태로 바꾸는 요청: 수락이 아니면 언제나 예외처리
		if (requestedStatus.equals("COMPLETED") && !currentStatus.equals("ACCEPTED")) {
			throw new OrderExceptions(COMPLETED_ONLY_ACCEPT);
		}

		// 상태 업데이트
		order.setStatus(OrderStatus.valueOf(requestedStatus));
		orderRepository.save(order);

		//완료 상태면 포인트 차감하고 구매건에 대한 포인트 추가
		if (order.getStatus().name().equals("COMPLETED")) {
			member.addPoints(-order.getTotalPrice());
			member.addPoints(order.getTotalPrice() * (0.03));
		}

		// 업데이트된 주문 정보를 포함한 응답 반환
		return new OrderResponseDto(order);
	}

	public void deleteOrder(Long orderId, Long memberId) {
		Order order = orderRepository.findById(orderId).orElseThrow(()
			->new OrderExceptions(NOT_FOUND_ORDER));

		// 권한 확인 : 주문한 사용자만 취소할 수 있도록 설정
		if (!order.getMember().getId().equals(memberId)) {
			throw new OrderExceptions(HAS_NOT_PERMISSION);
		}

		// 주문 상태가 완료되었는지 확인하고 완료된 주문은 취소 불가능하게 설정
		if(order.getStatus() == OrderStatus.COMPLETED) {
			throw new OrderExceptions(NOT_REJECTED_ACCEPT);
		}
		//소프트 삭제 수행
		order.delete();
	}
}
