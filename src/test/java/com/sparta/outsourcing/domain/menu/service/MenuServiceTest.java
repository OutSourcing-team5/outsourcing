package com.sparta.outsourcing.domain.menu.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.sql.Time;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.outsourcing.common.exception.customException.MenuExceptions;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

@DisplayName("menu 테스트")
@ExtendWith(MockitoExtension.class) // 추가
class MenuServiceTest {

	private Menu menu;
	private Store store;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private StoreRepository storeRepository;

	@InjectMocks
	private MenuService menuService;
	private MenuRequestDto menuRequestDto ;
	@BeforeEach
	void setUp() {
		store = new Store();
		menu = new Menu();
		Member member = new Member();
		menu = Menu.createOf("pizza", 10000, store);
		ReflectionTestUtils.setField(store, "id", 1L);

		menuRequestDto = new MenuRequestDto();
		menuRequestDto.setStoreId(store.getId());
		menuRequestDto.setMenuName(menu.getMenuName());
		menuRequestDto.setPrice(menu.getPrice());
	}

	@Test
	void 삭제된_메뉴에_업데이트_시도() {
		Long menuId = 1L;
		Long storeId = 1L;
		String newName = "chicken";
		int newPrice = 15000;
		Long currentMemberId = 1L;

		Store store = Store.createOf("My Store", Time.valueOf("09:00:00"), Time.valueOf("21:00:00"), 1000, new Member());
		given(storeRepository.findById(storeId)).willReturn(Optional.of(store));

		menu.delete();
		given(menuRepository.findById(menuId)).willReturn(Optional.of(menu));

		MenuExceptions thrownException = assertThrows(MenuExceptions.class, () -> menuService.updateMenu(menuId, menuRequestDto, currentMemberId));

		assertEquals("이미 삭제된 메뉴입니다", thrownException.getExceptionCode().getMessage());
	}

	@Test
	void testMenuCreate() {
		String menuName = menu.getMenuName();
		int price = menu.getPrice();

		assertEquals("pizza", menu.getMenuName());
		assertEquals(10000, menu.getPrice());
		assertFalse(menu.isInactive());
	}

	@Test
	void testMenuUpdate() {
		String menuName = "chicken";
		int price = 15000;

		assertEquals("pizza", menu.getMenuName());
		assertEquals(10000, menu.getPrice());
	}

	@Test
	void testMenuDelete() {
		menu.delete();

		assertTrue(menu.isInactive());
	}


}