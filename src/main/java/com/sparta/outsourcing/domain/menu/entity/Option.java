package com.sparta.outsourcing.domain.menu.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.outsourcing.domain.store.entity.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "options")
@NoArgsConstructor
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String optionName;

	@Column(nullable = false)
	private int optionPrice;

	@OneToMany(mappedBy = "option")
	private List<MenuOption> menuOptions = new ArrayList<>();

	private Option(String optionName, int optionPrice) {
		this.optionName = optionName;
		this.optionPrice = optionPrice;
	}

	public static Option createOf(String optionName, int optionPrice) {
		return new Option(optionName, optionPrice);
	}

	public void updateOption(String optionName, int optionPrice) {
		this.optionName = optionName;
		this.optionPrice = optionPrice;
	}
}
