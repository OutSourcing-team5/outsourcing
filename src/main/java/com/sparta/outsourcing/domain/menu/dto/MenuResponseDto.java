package com.sparta.outsourcing.domain.menu.dto;

import java.time.LocalDateTime;

import com.sparta.outsourcing.domain.menu.entity.Menu;

public class MenuResponseDto {
	//private long id;
	private String menuName;
	private int price;
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public MenuResponseDto(Menu menu) {
		this.menuName = menu.getMenuName();
		this.price = menu.getPrice();
		this.createdAt = menu.getCreatedAt();
		this.modifiedAt = menu.getModifiedAt();
	}
}
