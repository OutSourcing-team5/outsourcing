package com.sparta.outsourcing.domain.store.dto;

import java.sql.Time;

import com.sparta.outsourcing.domain.store.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreResponseDto {
	private Long id;
	private String storeName;
	private Time openTime;
	private Time closeTime;
	private int minPrice;
	private String createdAt;
	private String modifiedAt;

	public StoreResponseDto(Store store) {
		this.id = store.getId();
		this.storeName = store.getStoreName();
		this.openTime = store.getOpenTime();
		this.closeTime = store.getCloseTime();
		this.minPrice = store.getMinPrice();
		this.createdAt = store.getCreatedAt().toString();
		this.modifiedAt = store.getModifiedAt().toString();
	}
}
