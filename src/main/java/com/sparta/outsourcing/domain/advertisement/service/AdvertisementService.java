package com.sparta.outsourcing.domain.advertisement.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.common.exception.customException.AdvertisementExceptions;
import com.sparta.outsourcing.common.exception.customException.MemberExceptions;
import com.sparta.outsourcing.common.exception.customException.StoreExceptions;
import com.sparta.outsourcing.domain.advertisement.dto.StoreAdvertisementRequestDto;
import com.sparta.outsourcing.domain.advertisement.entity.Advertisement;
import com.sparta.outsourcing.domain.advertisement.repository.AdvertisementRepository;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
	private final AdvertisementRepository advertisementRepository;
	private final MemberRepository memberRepository;
	private final StoreRepository storeRepository;

	public void registerAdvertisement(StoreAdvertisementRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new AdvertisementExceptions(NOT_FOUND_USER)
		);
		Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
			() -> new AdvertisementExceptions(NOT_FOUND_USER)
		);
		boolean exist = advertisementRepository.existsByMemberAndStore(member, store);
		if(exist) {
			throw new AdvertisementExceptions(ALREADY_ADVERTISEMENT_EXISTS);
		}
		Advertisement advertisement = Advertisement.createOf(member, store);
		advertisementRepository.save(advertisement);
	}

	public void unregisterAdvertisement(StoreAdvertisementRequestDto advertisementRequestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new AdvertisementExceptions(NOT_FOUND_USER)
		);
		Store store = storeRepository.findById(advertisementRequestDto.getStoreId()).orElseThrow(
			() -> new AdvertisementExceptions(NOT_FOUND_STORE)
		);
		Advertisement advertisement = advertisementRepository.findByMemberAndStore(member, store)
			.orElseThrow(() -> new AdvertisementExceptions(NOT_FOUND_ADVERTISEMENT));

		advertisementRepository.delete(advertisement);
	}
}
