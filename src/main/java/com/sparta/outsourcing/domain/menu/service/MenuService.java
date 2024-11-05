package com.sparta.outsourcing.domain.menu.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.exception.customException.MenuExceptions;
import com.sparta.outsourcing.domain.menu.dto.MenuDeleteDto;
import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

	private final StoreRepository storeRepository;
	private final MenuRepository menuRepository;

	public MenuResponseDto createMenu(@Valid MenuRequestDto menuRequestDto, Long currentMemberId) {
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new MenuExceptions(NOT_FOUND_STORE));

		if(!currentMemberId.equals(store.getMember().getId())) {
			throw new MenuExceptions(ONLY_OWNER_ALLOWED);
		}

		if (store.isInactive()) {
			throw new MenuExceptions(STORE_OUT_OF_BUSINESS);
		}

		Menu menu = Menu.createOf(menuRequestDto.getMenuName(), menuRequestDto.getPrice(), store);
		return new MenuResponseDto(menuRepository.save(menu));
	}

	@Transactional
	public MenuResponseDto updateMenu(Long menuId, @Valid MenuRequestDto menuRequestDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuExceptions(NOT_FOUND_MENU));
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new MenuExceptions(NOT_FOUND_STORE));

		if(!store.getId().equals(menu.getStore().getId())) {
			throw new MenuExceptions(CANNOT_MODIFY_STORE_ID);
		}

		if (store.isInactive()) {
			throw new MenuExceptions(STORE_OUT_OF_BUSINESS);
		}

		if(!store.getMember().getId().equals(currentMemberId)) {
			throw new MenuExceptions(ONLY_OWNER_ALLOWED);
		}

		if(menu.isInactive()) {
			throw new MenuExceptions(MENU_ALREADY_DELETED);
		}

		menu.update(menuRequestDto.getMenuName(), menuRequestDto.getPrice());
		return new MenuResponseDto(menuRepository.save(menu));
	}

	@Transactional
	public void deleteMenu(Long menuId, MenuDeleteDto menuDeleteDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuExceptions(NOT_FOUND_MENU));
		Store store = storeRepository.findById(menuDeleteDto.getStoreId()).orElseThrow(() -> new MenuExceptions(NOT_FOUND_STORE));

		if(!store.getMember().getId().equals(currentMemberId)) {
			throw new MenuExceptions(ONLY_OWNER_ALLOWED);
		}

		if(menu.isInactive()) {
			throw new MenuExceptions(MENU_ALREADY_DELETED);
		}

		menu.delete();
	}
}
