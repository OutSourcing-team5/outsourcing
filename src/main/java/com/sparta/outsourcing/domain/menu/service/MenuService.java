package com.sparta.outsourcing.domain.menu.service;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.menu.dto.MenuCreateDto;
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

	public MenuResponseDto createMenu(@Valid MenuCreateDto menuCreateDto, MemberRole currentMemberRole, Long currentMemberId) {

		//가게가 없습니다
		Store store = storeRepository.findById(menuCreateDto.getStoreId()).orElseThrow(() -> new IllegalArgumentException("해당 가게가 없습니다"));

		//권한이 없습니다
		if(!currentMemberRole.equals(MemberRole.OWNER) && currentMemberId.equals(store.getId())) {
			throw new IllegalArgumentException("권한이 없습니다");
		}

		// 폐업한 가게인지 확인
		if (store.isDeleted()) {
			throw new IllegalArgumentException("폐업한 가게입니다");
		}

		Menu menu = new Menu(menuCreateDto, store);
		Menu menuSave = menuRepository.save(menu);
		return new MenuResponseDto(menuSave);
	}
}
