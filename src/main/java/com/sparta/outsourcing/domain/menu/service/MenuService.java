package com.sparta.outsourcing.domain.menu.service;

import org.springframework.stereotype.Service;

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

		//가게가 없습니다
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		//권한이 없습니다(가게는 사장님만 생성 가능해서 사장님일때 생성 가능한 검사 삭제)
		if(!currentMemberId.equals(store.getMember().getId())) {
			throw new IllegalArgumentException("권한이 없습니다");
		}

		// 폐업한 가게인지 확인
		if (store.isDeleted()) {
			throw new IllegalArgumentException("폐업한 가게입니다");
		}

		//Menu menu = new Menu(menuRequestDto, store);
		Menu menu = Menu.createOf(menuRequestDto.getMenuName(), menuRequestDto.getPrice(), store);
		return new MenuResponseDto(menuRepository.save(menu));
	}

	public MenuResponseDto updateMenu(Long menuId, @Valid MenuRequestDto menuRequestDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 없습니다"));
		Store store = storeRepository.findById(menuRequestDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		//storeID를 남의 가게로 변경 못하도록
		if(!store.getId().equals(menu.getStore().getId())) {
			throw new IllegalArgumentException("남의 가게 정보는 수정할 수 없습니다");
		}

		//폐업한 가게입니다
		if (store.isDeleted()) {
			throw new IllegalArgumentException("폐업한 가게입니다");
		}

		//권한이 없습니다(가게는 사장님만 생성 가능해서 사장님일때 생성 가능한 검사 삭제)
		if(!store.getMember().getId().equals(currentMemberId)) {
			throw new IllegalArgumentException("권한이 없습니다");
		}

		//삭제된 메뉴입니다
		if(menu.isDeleted()) {
			throw new IllegalArgumentException("이미 삭제된 메뉴입니다");
		}

		menu.update(menuRequestDto.getMenuName(), menuRequestDto.getPrice(), store);
		return new MenuResponseDto(menuRepository.save(menu));
	}
}
