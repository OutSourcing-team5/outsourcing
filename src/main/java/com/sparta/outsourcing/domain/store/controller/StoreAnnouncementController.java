package com.sparta.outsourcing.domain.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.store.dto.AnnouncementRequestDto;
import com.sparta.outsourcing.domain.store.service.StoreAnnouncementService;
import com.sparta.outsourcing.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreAnnouncementController {

	private final StoreAnnouncementService storeAnnouncementService;

	@PutMapping("/{storeId}/announcement")
	public ResponseEntity<Void> updateAnnouncement(
		@PathVariable Long storeId,
		@RequestBody AnnouncementRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		storeAnnouncementService.updateAnnouncement(storeId, requestDto, memberId);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{storeId}/announcement")
	public ResponseEntity<Void> deleteAnnouncement(
		@PathVariable Long storeId,
		@RequestAttribute("id") Long memberId
	) {
		storeAnnouncementService.deleteAnnouncement(storeId, memberId);
		return ResponseEntity.noContent().build();
	}
}
