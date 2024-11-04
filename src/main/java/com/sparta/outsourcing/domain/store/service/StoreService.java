package com.sparta.outsourcing.domain.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseFromStoreDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseFromStoreDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.dto.DetailedStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.ShortStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreUpdateRequestDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;
	private final MenuRepository menuRepository;
	private final ReviewRepository reviewRepository;

	public StoreResponseDto createStore(StoreRequestDto requestDto, Long memberId) {
		Member storeOwner = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 유저가 없습니다.")
		);

		if (!storeOwner.getRole().equals(MemberRole.OWNER)) {
			throw new IllegalArgumentException("사장님 권한 회원만 가게를 생성할 수 있습니다.");
		}

		if (storeRepository.countAllByMemberAndIsDeletedFalse(storeOwner) == 3) {
			throw new IllegalArgumentException("해당 사장님은 이미 3개의 가게를 소유하고 있습니다.");
		}

		Store store = Store.createOf(requestDto.getStoreName(), requestDto.getOpenTime(), requestDto.getCloseTime(), requestDto.getMinPrice(), storeOwner);
		storeRepository.save(store);

		return new StoreResponseDto(store);
	}

	public Page<ShortStoreResponseDto> getAllStoreByName(String storeName, int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());
		Page<Store> stores = storeRepository.findAllByStoreNameContaining(storeName, pageable);
		return stores.map(store -> new ShortStoreResponseDto(store.getId(), store.getStoreName()));
	}

	public Page<ShortStoreResponseDto> getAllStore(int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());
		Page<Store> stores = storeRepository.findAll(pageable);
		return stores.map(store -> new ShortStoreResponseDto(store.getId(), store.getStoreName()));
	}

	public DetailedStoreResponseDto getOneStore(Long storeId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 가게가 없습니다.")
		);

		Pageable menuPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Menu> menus = menuRepository.findAllByStoreContaining(store, menuPageable);

		Pageable reviewPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Review> reviews = reviewRepository.findAllByStoreContaining(store, reviewPageable);

		return new DetailedStoreResponseDto(
			store,
			menus.map(MenuResponseFromStoreDto::new),
			reviews.map(ReviewResponseFromStoreDto::new)
		);
	}

	public StoreResponseDto updateStore(Long storeId, @Valid StoreUpdateRequestDto requestDto, Long memberId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 가게가 없습니다.")
		);

		if (store.isDeleted()) {
			throw new IllegalArgumentException("폐업한 가게입니다.");
		}

		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 유저가 없습니다.")
		);

		if (member.getId() != store.getMember().getId()) {
			throw new IllegalArgumentException("가게의 사장님만 수정할 수 있습니다.");
		}

		store.update(requestDto.getOpenTime(), requestDto.getCloseTime(), requestDto.getMinPrice(), requestDto.isOpened());

		return new StoreResponseDto(store);
	}

	public void deleteStore(Long storeId, Long memberId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 가게가 없습니다.")
		);

		if (store.isDeleted()) {
			throw new IllegalArgumentException("이미 폐업한 가게입니다.");
		}

		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new IllegalArgumentException("해당하는 유저가 없습니다.")
		);

		if (member.getId() != store.getMember().getId()) {
			throw new IllegalArgumentException("가게의 사장님만 수정할 수 있습니다.");
		}

		store.delete();
	}
}
