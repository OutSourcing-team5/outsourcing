package com.sparta.outsourcing.domain.store.dto;

import java.sql.Time;

import org.springframework.data.domain.Page;

import com.sparta.outsourcing.domain.menu.dto.MenuResponseFromStoreDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseFromStoreDto;
import com.sparta.outsourcing.domain.store.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryStoreResponseDto {
	private Long id;
	private String storeName;
	private Time openTime;
	private Time closeTime;
	private int minPrice;
	private String announcement;
	private String createdAt;
	private String modifiedAt;
	private Page<MenuResponseFromStoreDto> menus;

	public CategoryStoreResponseDto(
		Store store, Page<MenuResponseFromStoreDto> menus) {
		this.id = store.getId();
		this.storeName = store.getStoreName();
		this.openTime = store.getOpenTime();
		this.closeTime = store.getCloseTime();
		this.minPrice = store.getMinPrice();
		this.announcement = store.getAnnouncement();
		this.createdAt = store.getCreatedAt().toString();
		this.modifiedAt = store.getModifiedAt().toString();
		this.menus = menus;
	}
}
