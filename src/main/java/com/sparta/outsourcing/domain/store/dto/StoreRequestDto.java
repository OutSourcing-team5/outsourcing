package com.sparta.outsourcing.domain.store.dto;

import java.sql.Time;

import lombok.Getter;

@Getter
public class StoreRequestDto {
	private String storeName;
	private Time openTime;
	private Time closeTime;
	private int minPrice;
}
