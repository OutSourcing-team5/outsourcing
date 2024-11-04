package com.sparta.outsourcing.domain.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.menu.dto.MenuDeleteDto;
import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;

	@PostMapping("")
	public ResponseEntity<MenuResponseDto> createMenu(
		@Valid @RequestBody MenuRequestDto menuRequestDto,
		@RequestAttribute("id") Long currentMemberId
	) {
		MenuResponseDto menuResponseDto = menuService.createMenu(menuRequestDto, currentMemberId);

		return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
	}

	@PutMapping("/{menuId}")
	public ResponseEntity<MenuResponseDto> updateMenu(
		@PathVariable Long menuId,
		@Valid @RequestBody MenuRequestDto menuRequestDto,
		@RequestAttribute("id") Long currentMemberId
	) {
		MenuResponseDto menuResponseDto = menuService.updateMenu(menuId, menuRequestDto, currentMemberId);

		return ResponseEntity.status(HttpStatus.OK).body(menuResponseDto);
	}

	@DeleteMapping("/{menuId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMenu(
		@PathVariable Long menuId,
		@Valid @RequestBody MenuDeleteDto menuDeleteDto,
		@RequestAttribute("id") Long currentMemberId
	) {
		menuService.deleteMenu(menuId, menuDeleteDto, currentMemberId);
	}
}
