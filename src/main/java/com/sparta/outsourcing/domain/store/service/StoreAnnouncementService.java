package com.sparta.outsourcing.domain.store.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.exception.customException.StoreExceptions;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.store.dto.AnnouncementRequestDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreAnnouncementService {
	
	private final StoreRepository storeRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public void updateAnnouncement(Long storeId, AnnouncementRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_STORE)
		);

		if (store.getMember().getId() != member.getId()) {
			throw new StoreExceptions(ONLY_OWNER_ALLOWED);
		}

		if (store.isInactive()) {
			throw new StoreExceptions(STORE_OUT_OF_BUSINESS);
		}

		store.updateAnnouncement(requestDto.getAnnouncement());
	}

	@Transactional
	public void deleteAnnouncement(Long storeId, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_USER)
		);

		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new StoreExceptions(NOT_FOUND_STORE)
		);

		if (store.getMember().getId() != member.getId()) {
			throw new StoreExceptions(ONLY_OWNER_ALLOWED);
		}

		if (store.isInactive()) {
			throw new StoreExceptions(STORE_OUT_OF_BUSINESS);
		}

		store.deleteAnnouncement();
	}
}
