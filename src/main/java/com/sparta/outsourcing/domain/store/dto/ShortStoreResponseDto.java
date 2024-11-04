package com.sparta.outsourcing.domain.store.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShortStoreResponseDto {
	public Long id;
	public String storeName;

	public ShortStoreResponseDto(Long id, String storeName) {
		this.id = id;
		this.storeName = storeName;
	}
}
