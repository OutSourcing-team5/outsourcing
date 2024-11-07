package com.sparta.outsourcing.domain.store.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.exception.customException.StoreExceptions;
import com.sparta.outsourcing.domain.advertisement.repository.AdvertisementRepository;
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
	private final AdvertisementRepository advertisementRepository;

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

		// 광고된 가게는 즐겨찾기와 일반가게와 상관없이 맨위에 항상 조회
		List<Store> advertisedStoresWithName = advertisementRepository.findStoresByStoreNameContainingAndInactiveFalse(storeName);
		// 즐겨찾기한 가게 중 이름이 일치하는 가게 리스트 조회
		List<Store> likedStoresWithName = likeRepository.findStoresByMemberAndInactiveFalseAndStoreNameContainingOrderByModifiedAtDesc(member, storeName);

		List<Store> combinedStores;
		if (likedStoresWithName.isEmpty()) {
			// 즐겨찾기한 가게가 없으면 전체 가게 중 이름이 일치하는 가게를 페이지네이션하여 조회
			Page<Store> allStoresWithName = storeRepository.findAllByInactiveFalseAndStoreNameContaining(storeName, pageable);
			combinedStores = allStoresWithName.getContent();
		} else {
			// 즐겨찾기한 가게가 있으면 일반 가게와 합쳐서 반환
			List<Long> likedStoresWithNameIds = likedStoresWithName.stream().map(Store::getId).toList();
			Page<Store> generalStoresWithName = storeRepository.findAllByInactiveFalseAndStoreNameContainingAndIdNotIn(storeName, likedStoresWithNameIds, pageable);
			combinedStores = Stream.concat(likedStoresWithName.stream(), generalStoresWithName.getContent().stream()).toList();
		}

		return Stream.concat(
			advertisedStoresWithName.stream().map(store -> new ShortStoreResponseDto(store, true)), // 광고된 가게는 isAdvertisement = true
			combinedStores.stream().map(store -> new ShortStoreResponseDto(store, false))
		).toList();
	}

	public List<ShortStoreResponseDto> getAllStore(int page, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		Pageable pageable = PageRequest.of(page, 5, Sort.by("modifiedAt").descending());

		List<Store> advertisedStores = advertisementRepository.findAllByInactiveFalse();
		// 즐겨찾기 가게 리스트 조회
		List<Store> likedStores = likeRepository.findStoresByMemberAndInactiveFalseOrderByModifiedAtDesc(member);

		List<Store> combinedStores;
		if (likedStores.isEmpty()) {
			// 즐겨찾기한 가게가 없으면 전체 가게를 페이지네이션하여 조회
			Page<Store> allStores = storeRepository.findAllByInactiveFalse(pageable);
			combinedStores = allStores.getContent();
		} else {
			// 즐겨찾기한 가게가 있으면 일반 가게와 합쳐서 반환
			List<Long> likedStoreIds = likedStores.stream().map(Store::getId).toList();
			Page<Store> generalStores = storeRepository.findAllByInactiveFalseAndIdNotIn(likedStoreIds, pageable);

			combinedStores = Stream.concat(likedStores.stream(), generalStores.getContent().stream()).toList();
		}

		// 광고된 가게 + 즐겨찾기 가게 + 일반 가게 순으로 반환
		return Stream.concat(
			advertisedStores.stream().map(store -> new ShortStoreResponseDto(store, true)), // 광고된 가게는 isAdvertisement = true
			combinedStores.stream().map(store -> new ShortStoreResponseDto(store, false))
		).toList();
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

	// ========== 스케쥴러 ==========

	// @Scheduled(cron = "0 */10 * * * *")		// 10분마다 실행
	@Scheduled(cron = "*/10 * * * * *")
	public void updateStoreStatus() {
		List<Store> stores = storeRepository.findAllByInactiveFalse();
		LocalTime currentTime = LocalTime.now();

		for (Store store : stores) {
			Time openTime = store.getOpenTime();
			Time closeTime = store.getCloseTime();

			store.updateOpenStatus(currentTime.isAfter(openTime.toLocalTime()) && currentTime.isBefore(closeTime.toLocalTime()));
			storeRepository.save(store);
		}
	}
}
