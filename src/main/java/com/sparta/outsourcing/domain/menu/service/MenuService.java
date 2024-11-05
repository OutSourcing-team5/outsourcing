package com.sparta.outsourcing.domain.menu.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		if(!currentMemberId.equals(store.getMember().getId())) {
			throw new IllegalArgumentException("해당 기능은 사장님만 가능합니다");
		}

		if (store.isDelete()) {
			throw new IllegalArgumentException("폐업한 가게입니다");
		}

		Menu menu = Menu.createOf(menuRequestDto.getMenuName(), menuRequestDto.getPrice(), store);
		return new MenuResponseDto(menuRepository.save(menu));
	}

	@Transactional
	public MenuResponseDto updateMenu(Long menuId, @Valid MenuRequestDto menuRequestDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다"));
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		if(!store.getId().equals(menu.getStore().getId())) {
			throw new IllegalArgumentException("남의 가게 정보는 수정할 수 없습니다");
		}

		if (store.isDelete()) {
			throw new IllegalArgumentException("폐업한 가게입니다");
		}

		if(!store.getMember().getId().equals(currentMemberId)) {
			throw new IllegalArgumentException("해당 기능은 사장님만 가능합니다");
		}

		if(menu.isDelete()) {
			throw new IllegalArgumentException("이미 삭제된 메뉴입니다");
		}

		menu.update(menuRequestDto.getMenuName(), menuRequestDto.getPrice());
		return new MenuResponseDto(menuRepository.save(menu));
	}

	@Transactional
	public void deleteMenu(Long menuId, MenuDeleteDto menuDeleteDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다"));
		Store store = storeRepository.findById(menuDeleteDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		if(!store.getMember().getId().equals(currentMemberId)) {
			throw new IllegalArgumentException("해당 기능은 사장님만 가능합니다");
		}

		if(menu.isDelete()) {
			throw new IllegalArgumentException("이미 삭제된 메뉴입니다");
		}

		menu.delete();
	}
}
