package com.sparta.outsourcing.domain.menu.dto;

import com.sparta.outsourcing.domain.menu.entity.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponseFromStoreDto {
	private String menuName;
	private int price;

	public MenuResponseFromStoreDto(Menu menu) {
		this.menuName = menu.getMenuName();
		this.price = menu.getPrice();
	}
}
