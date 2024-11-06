package com.sparta.outsourcing.domain.menu.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;
import static java.awt.SystemColor.*;

import java.util.List;

import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.stereotype.Service;

import com.sparta.outsourcing.common.exception.customException.MenuExceptions;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.dto.OptionRequestDto;
import com.sparta.outsourcing.domain.menu.dto.OptionResponse;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.entity.MenuOption;
import com.sparta.outsourcing.domain.menu.entity.Option;
import com.sparta.outsourcing.domain.menu.repository.MenuOptionRepository;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.menu.repository.OptionRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionService {

	private final OptionRepository optionRepository;
	private final MenuRepository menuRepository;
	private final MenuOptionRepository menuOptionRepository;

	public OptionResponse createOption(@Valid OptionRequestDto optionRequestDto, Long currentMemberId) {
		Menu menu = menuRepository.findById(optionRequestDto.getMenuId()).orElseThrow(() -> new MenuExceptions(NOT_FOUND_MENU));

		Option option = Option.createOf(optionRequestDto.getOptionName(), optionRequestDto.getOptionPrice());
		optionRepository.save(option);

		MenuOption menuOption = MenuOption.createOf(menu, option, optionRequestDto.getOptionPrice());
		menuOptionRepository.save(menuOption);

		return new OptionResponse(optionRepository.save(option));
	}

	public OptionResponse updateOption(Long optionId, @Valid OptionRequestDto optionRequestDto) {
		Option option = optionRepository.findById(optionId).orElseThrow(() -> new RuntimeException("Option not found"));

		option.updateOption(optionRequestDto.getOptionName(), optionRequestDto.getOptionPrice());

		return new OptionResponse(optionRepository.save(option));
	}

	public void deleteOption(Long optionId) {
		Option option = optionRepository.findById(optionId).orElseThrow(() -> new RuntimeException("Option not found"));

		optionRepository.delete(option);
	}
}
