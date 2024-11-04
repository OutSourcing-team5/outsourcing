package com.sparta.outsourcing.domain.store.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.store.dto.ShortStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;

	public StoreResponseDto createStore(StoreRequestDto requestDto, Long memberId) {
		Member storeOwner = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 유저가 없습니다.")
		);

		if (!storeOwner.getRole().equals(MemberRole.OWNER)) {
			throw new IllegalArgumentException("사장님 권한 회원만 가게를 생성할 수 있습니다.");
		}

		if (storeRepository.countAllByMember(storeOwner) == 3) {
			throw new IllegalArgumentException("해당 사장님은 이미 3개의 가게를 소유하고 있습니다.");
		}

		Store store = Store.create(requestDto.getStoreName(), requestDto.getOpenTime(), requestDto.getCloseTime(), requestDto.getMinPrice());
		storeRepository.save(store);

		return new StoreResponseDto(store);
	}

	public Page<ShortStoreResponseDto> getAllStoreByName(String storeName, int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());
		Page<Store> stores = storeRepository.findAllByStoreNameContaining(storeName, pageable);
		return stores.map(this::toShortStoreResponseDto);
	}

	public Page<ShortStoreResponseDto> getAllStore(int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());
		Page<Store> stores = storeRepository.findAll(pageable);
		return stores.map(this::toShortStoreResponseDto);
	}

	// ============== 편의 메서드 ==============

	private ShortStoreResponseDto toShortStoreResponseDto(Store store) {
		return new ShortStoreResponseDto(store.getId(), store.getStoreName());
	}
}
