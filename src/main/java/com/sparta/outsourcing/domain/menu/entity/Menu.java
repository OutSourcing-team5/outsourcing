package com.sparta.outsourcing.domain.menu.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.outsourcing.domain.TimeStamped;
import com.sparta.outsourcing.domain.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "menus")
@NoArgsConstructor
public class Menu extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String menuName;

	@Column(nullable = false)
	private int price;

	@Column(nullable = false)
	private boolean inactive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	private Store store;

	@Column(nullable = false)
	private String category;

	@OneToMany(mappedBy = "menu")
	private List<MenuOption> menuOptions = new ArrayList<>();

	private Menu(String menuName, int price, Store store, String category) {
		this.menuName = menuName;
		this.price = price;
		this.inactive = false;
		this.store = store;
		this.category = category;
	}

	public static Menu createOf(String menuName, int price, Store store, String category) {
		return new Menu(menuName, price, store, category);
	}

	public void update(String menuName, int price, String category) {
		this.menuName = menuName;
		this.price = price;
		this.category = category;
	}

	public void delete() {
		this.inactive = true;
	}
}
