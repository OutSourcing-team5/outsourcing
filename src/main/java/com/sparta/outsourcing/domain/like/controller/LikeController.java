package com.sparta.outsourcing.domain.like.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.like.dto.StoreLikeRequestDto;
import com.sparta.outsourcing.domain.like.service.LikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/like")
	public ResponseEntity<Void> likeStore(
		@RequestBody StoreLikeRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		likeService.likeStore(requestDto, memberId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/unlike")
	public ResponseEntity<Void> unlikeStore(
		@RequestBody StoreLikeRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		likeService.unlikeStore(requestDto, memberId);
		return ResponseEntity.noContent().build();
	}
}
