package com.sparta.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortStoreResponseDto {
	private Long id;
	private String storeName;

	public ShortStoreResponseDto(Long id, String storeName) {
		this.id = id;
		this.storeName = storeName;
	}
}
