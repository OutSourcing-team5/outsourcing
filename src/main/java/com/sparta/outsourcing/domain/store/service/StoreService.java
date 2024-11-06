package com.sparta.outsourcing.domain.store.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.exception.customException.StoreExceptions;
import com.sparta.outsourcing.domain.like.repository.LikeRepository;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseFromStoreDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseFromStoreDto;
import com.sparta.outsourcing.domain.review.entity.Review;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.dto.CategoryStoreResponseDto;
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
	private final LikeRepository likeRepository;

	public StoreResponseDto createStore(StoreRequestDto requestDto, Long memberId) {
		Member storeOwner = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		if (!storeOwner.getRole().equals(MemberRole.OWNER)) {
			throw new StoreExceptions(ONLY_OWNER_ALLOWED);
		}

		if (storeRepository.countAllByMemberAndInactiveFalse(storeOwner) == 3) {
			throw new StoreExceptions(CANNOT_EXCEED_STORE_LIMIT);
		}

		Store store = Store.createOf(requestDto.getStoreName(), requestDto.getOpenTime(), requestDto.getCloseTime(), requestDto.getMinPrice(), storeOwner);
		Store savedStore = storeRepository.save(store);

		return new StoreResponseDto(savedStore);
	}

	public List<ShortStoreResponseDto> getAllStoreByName(String storeName, int page, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());

		List<Store> likedStoresWithName = likeRepository.findStoresByMemberAndInactiveFalseAndStoreNameContainingOrderByModifiedAtDesc(member, storeName);
		List<Long> likedStoresWithNameIds = likedStoresWithName.stream().map(Store::getId).toList();

		Page<Store> generalStoresWithName = storeRepository.findAllByInactiveFalseAndStoreNameContainingAndIdNotIn(storeName, likedStoresWithNameIds, pageable);

		return Stream.concat(likedStoresWithName.stream(), generalStoresWithName.getContent().stream())
			.map(ShortStoreResponseDto::new).toList();
	}

	public List<ShortStoreResponseDto> getAllStore(int page, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());

		List<Store> likedStores = likeRepository.findStoresByMemberAndInactiveFalseOrderByModifiedAtDesc(member);
		List<Long> likedStoreIds = likedStores.stream().map(Store::getId).toList();

		Page<Store> generalStores = storeRepository.findAllByInactiveFalseAndIdNotIn(likedStoreIds, pageable);

		return Stream.concat(likedStores.stream(), generalStores.getContent().stream())
			.map(ShortStoreResponseDto::new).toList();
	}

	public DetailedStoreResponseDto getOneStore(Long storeId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_STORE)
		);

		Pageable menuPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Menu> menus = menuRepository.findAllByStoreAndInactiveFalse(store, menuPageable);

		Pageable reviewPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Review> reviews = reviewRepository.findAllByStoreAndInactiveFalse(store, reviewPageable);

		return new DetailedStoreResponseDto(
			store,
			menus.map(MenuResponseFromStoreDto::new),
			reviews.map(ReviewResponseFromStoreDto::new)
		);
	}

	public CategoryStoreResponseDto getOneStoreCategory(Long storeId, String category) {
		Store store = storeRepository.findById(storeId).orElseThrow(() -> new StoreExceptions(NOT_FOUND_STORE));

		Pageable menuPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Menu> menus = menuRepository.findAllByStoreAndCategoryAndInactiveFalse(store, category, menuPageable);
		if(menus.isEmpty()) {
			throw new StoreExceptions(NOT_FOUND_CATEGORY);
		}

		Pageable reviewPageable = PageRequest.of(0, 5, Sort.by("modifiedAt").descending());
		Page<Review> reviews = reviewRepository.findAllByStoreAndInactiveFalse(store, reviewPageable);

		return new CategoryStoreResponseDto(
			store,
			menus.map(MenuResponseFromStoreDto::new)
		);
	}

	@Transactional
	public StoreResponseDto updateStore(Long storeId, @Valid StoreUpdateRequestDto requestDto, Long memberId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_STORE)
		);

		if (store.isInactive()) {
			throw new StoreExceptions(STORE_OUT_OF_BUSINESS);
		}

		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		if (member.getId() != store.getMember().getId()) {
			throw new StoreExceptions(ONLY_OWNER_ALLOWED);
		}

		store.update(requestDto.getOpenTime(), requestDto.getCloseTime(), requestDto.getMinPrice(), requestDto.isOpen());

		return new StoreResponseDto(store);
	}

	public void deleteStore(Long storeId, Long memberId) {
		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_STORE)
		);

		if (store.isInactive()) {
			throw new StoreExceptions(STORE_OUT_OF_BUSINESS);
		}

		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		if (member.getId() != store.getMember().getId()) {
			throw new StoreExceptions(ONLY_OWNER_ALLOWED);
		}

		store.delete();

		List<Menu> menus = menuRepository.findAllByStoreAndInactiveFalse(store);
		menus.forEach(Menu::delete);
		menuRepository.saveAll(menus);
	}
}
