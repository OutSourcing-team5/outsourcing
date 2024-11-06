package com.sparta.outsourcing.domain.store.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

	@InjectMocks
	private StoreService storeService;

	@Mock
	private StoreRepository storeRepository;

	@Mock
	private MemberRepository memberRepository;

	@Mock
	private MenuRepository menuRepository;

	@Mock
	private ReviewRepository reviewRepository;

	@Test
	@DisplayName("정상적인 가게 생성이 이루어집니다.")
	void 가게생성_성공() {
		// given
		Member owner = Member.createOf(null, null, null, null, MemberRole.OWNER);
		StoreRequestDto requestDto = new StoreRequestDto("test store", Time.valueOf("09:00:00"), Time.valueOf("21:30:00"), 10000);
		Store store = mock(Store.class);

		doReturn(LocalDateTime.now()).when(store).getCreatedAt();
		doReturn(LocalDateTime.now()).when(store).getModifiedAt();
		doReturn("test store").when(store).getStoreName();
		doReturn(Time.valueOf("09:00:00")).when(store).getOpenTime();
		doReturn(Time.valueOf("21:30:00")).when(store).getCloseTime();
		doReturn(10000).when(store).getMinPrice();

		storeService = new StoreService(storeRepository, memberRepository, menuRepository, reviewRepository);

		// stub
		when(memberRepository.findById(1L)).thenReturn(Optional.of(owner));
		when(storeRepository.save(any(Store.class))).thenReturn(store);

		// when
		StoreResponseDto responseDto = storeService.createStore(requestDto, 1L);

		// then
		assertEquals("test store", responseDto.getStoreName());
		assertEquals(Time.valueOf("09:00:00"), responseDto.getOpenTime());
		assertEquals(Time.valueOf("21:30:00"), responseDto.getCloseTime());
		assertEquals(10000, responseDto.getMinPrice());
	}
}