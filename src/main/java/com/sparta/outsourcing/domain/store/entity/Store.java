package com.sparta.outsourcing.domain.store.entity;

import java.sql.Time;

import com.sparta.outsourcing.domain.TimeStamped;
import com.sparta.outsourcing.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stores")
@NoArgsConstructor
public class Store extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String storeName;

	@Column(nullable = false)
	private Time openTime;

	@Column(nullable = false)
	private Time closeTime;

	@Column(nullable = false)
	private int minPrice;

	@Column(nullable = false)
	private boolean open;

	@Column(nullable = false)
	private boolean inactive;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;



	private Store(String storeName, Time openTime, Time closeTime, int minPrice, Member member) {
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minPrice = minPrice;
		this.open = true;
		this.inactive = false;
		this.member = member;
	}

	public static Store createOf(String storeName, Time openTime, Time closeTime, int minPrice, Member member) {
		return new Store(storeName, openTime, closeTime, minPrice, member);
	}

	public void update(Time openTime, Time closeTime, int minPrice, boolean opened) {
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minPrice = minPrice;
		this.open = opened;
	}

	public void delete() {
		this.inactive = true;
	}
}
