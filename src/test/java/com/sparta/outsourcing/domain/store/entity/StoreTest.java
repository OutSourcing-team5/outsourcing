package com.sparta.outsourcing.domain.store.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sparta.outsourcing.domain.member.entity.Member;

class StoreTest {

	private Store store;
	private Member member;

	@BeforeEach
	void setUp() {
		member = new Member();
		store = Store.createOf("Test store", Time.valueOf("09:00:00"), Time.valueOf("21:00:00"), 10000, member);
	}

	@Test
	void testCreateStore() {
		assertEquals("Test store", store.getStoreName());
		assertEquals(Time.valueOf("09:00:00"), store.getOpenTime());
		assertEquals(Time.valueOf("21:00:00"), store.getCloseTime());
		assertEquals(10000, store.getMinPrice());
		assertTrue(store.isOpen());
		assertFalse(store.isInactive());
		assertEquals(member, store.getMember());
	}

	@Test
	void testUpdateStore() {
		store.update(Time.valueOf("10:00:00"), Time.valueOf("20:00:00"), 15000, false);

		assertEquals(Time.valueOf("10:00:00"), store.getOpenTime());
		assertEquals(Time.valueOf("20:00:00"), store.getCloseTime());
		assertEquals(15000, store.getMinPrice());
		assertFalse(store.isOpen());
	}

	@Test
	void testDeleteStore() {
		store.delete();

		assertTrue(store.isInactive());
	}
}