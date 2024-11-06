package com.sparta.outsourcing.domain.like.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.common.exception.customException.LikeExceptions;
import com.sparta.outsourcing.domain.like.dto.StoreLikeRequestDto;
import com.sparta.outsourcing.domain.like.entity.Like;
import com.sparta.outsourcing.domain.like.repository.LikeRepository;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final MemberRepository memberRepository;
	private final StoreRepository storeRepository;

	public void likeStore(StoreLikeRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new LikeExceptions(NOT_FOUND_USER)
		);

		Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
			() -> new LikeExceptions(NOT_FOUND_STORE)
		);

		List<Like> existLike = likeRepository.findAllByMemberAndStore(member, store);
		if (!existLike.isEmpty()) {
			throw new LikeExceptions(ALREADY_LIKE_EXISTS);
		}

		if (store.isInactive()) {
			throw new LikeExceptions(STORE_OUT_OF_BUSINESS);
		}

		Like like = Like.createOf(member, store);
		likeRepository.save(like);
	}

	public void unlikeStore(StoreLikeRequestDto requestDto, Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(
			() -> new LikeExceptions(NOT_FOUND_USER)
		);

		Store store = storeRepository.findById(requestDto.getStoreId()).orElseThrow(
			() -> new LikeExceptions(NOT_FOUND_STORE)
		);

		Like like = likeRepository.findByMemberAndStore(member, store).orElseThrow(
			() -> new LikeExceptions(NOT_FOUND_LIKE)
		);

		likeRepository.delete(like);
	}
}
