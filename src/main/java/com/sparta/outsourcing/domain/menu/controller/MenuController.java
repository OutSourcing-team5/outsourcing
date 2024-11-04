package com.sparta.outsourcing.domain.menu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.menu.dto.MenuCreateDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.service.MenuService;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;

	@PostMapping("")
	public ResponseEntity<MenuResponseDto> createMenu(
		@Valid @RequestBody MenuCreateDto menuCreateDto,
		@RequestAttribute("role") MemberRole currentMemberRole,
		@RequestAttribute("id") Long currentMemberId
	) {
		MenuResponseDto menuResponseDto = menuService.createMenu(menuCreateDto, currentMemberRole, currentMemberId);

		return ResponseEntity.status(HttpStatus.CREATED).body(menuResponseDto);
	}
}
