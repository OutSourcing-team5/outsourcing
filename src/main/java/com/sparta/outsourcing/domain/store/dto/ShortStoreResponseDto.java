package com.sparta.outsourcing.domain.store.dto;

import com.sparta.outsourcing.domain.store.entity.Store;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortStoreResponseDto {
	private Long id;
	private String storeName;

	public ShortStoreResponseDto(Store store, boolean isAdvertisement) {
		this.id = store.getId();
		this.storeName = isAdvertisement ? store.getStoreName() + "[Advertisement]" : store.getStoreName();
	}
}
