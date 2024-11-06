package com.sparta.outsourcing.domain.menu.entity;

import com.sparta.outsourcing.domain.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menu_option")
@NoArgsConstructor
public class MenuOption {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "menu_id")
	private Menu menu;

	@ManyToOne
	@JoinColumn(name = "option_id")
	private Option option;

	@Column(nullable = false)
	private int price;

	private MenuOption(Menu menu, Option option, int price) {
		this.menu = menu;
		this.option = option;
		this.price = price;
	}

	public static MenuOption createOf(Menu menu, Option option, int price) {
		return new MenuOption(menu, option, price);
	}

}
