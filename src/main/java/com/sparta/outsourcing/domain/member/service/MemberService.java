package com.sparta.outsourcing.domain.member.service;

import static com.sparta.outsourcing.common.exception.enums.ExceptionCode.*;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.ParserInterpreter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.JwtUtil;
import com.sparta.outsourcing.common.PasswordEncoder;
import com.sparta.outsourcing.common.exception.customException.MemberExceptions;
import com.sparta.outsourcing.domain.member.dto.AddPointsRequestDto;
import com.sparta.outsourcing.domain.member.dto.DeleteMemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.LoginRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.dto.UpdateMemberRequestDto;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        //email 중복확인
        Optional<Member> checkEmail = memberRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new MemberExceptions(DUPLICATE_EMAIL);
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 사용자 등록
        Member member = Member.createOf(requestDto.getUsername(), encodedPassword, requestDto.getEmail(), requestDto.getAddress(), requestDto.getRole());
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

	public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new MemberExceptions(NOT_FOUND_USER)
        );
        if (!this.passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberExceptions(NOT_MATCH_PASSWORD);
        }
        String token = jwtUtil.createToken(member.getEmail());
        jwtUtil.addJwtToCookie(token, response);
	}

	public void deleteMember(DeleteMemberRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
            () -> new MemberExceptions(NOT_FOUND_USER)
        );

        if (!requestDto.getMemberId().equals(memberId)) {
            throw new MemberExceptions(HAS_NOT_PERMISSION);
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())) {
            throw new MemberExceptions(NOT_MATCH_PASSWORD);
        }

        if (member.isInactive()) {
            throw new MemberExceptions(MEMBER_ALREADY_DELETED);
        }

        member.delete();

        List<Store> stores = storeRepository.findAllByMemberAndInactiveFalse(member);
        stores.forEach(store -> {
            List<Menu> menus = menuRepository.findAllByStoreAndInactiveFalse(store);
            menus.forEach(Menu::delete);
            menuRepository.saveAll(menus);
            store.delete();
            }
        );
        storeRepository.saveAll(stores);

        List<Order> orders = orderRepository.findAllByMemberAndInactiveFalse(member);
        orders.forEach(Order::delete);
        orderRepository.saveAll(orders);
	}

    @Transactional
    public void updateMember(UpdateMemberRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(requestDto.getMemberId()).orElseThrow(
            () -> new MemberExceptions(NOT_FOUND_USER)
        );

        if (!requestDto.getMemberId().equals(memberId)) {
            throw new MemberExceptions(HAS_NOT_PERMISSION);
        }

        if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())) {
            throw new MemberExceptions(NOT_MATCH_PASSWORD);
        }

        String encodedPassword = "";

        if (requestDto.getNewPassword() != null && !requestDto.getNewPassword().isEmpty()) {
            encodedPassword = passwordEncoder.encode(requestDto.getNewPassword());
        }

        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new MemberExceptions(USERNAME_REQUIRED);
        }

        if (requestDto.getAddress() == null || requestDto.getAddress().isEmpty()) {
            throw new MemberExceptions(ADDRESS_REQUIRED);
        }

        member.update(requestDto.getUsername(), encodedPassword, requestDto.getAddress());
        memberRepository.save(member);
    }

    public MemberResponseDto addPoints(@Valid AddPointsRequestDto requestDto, Long currentMemberId) {
        Member member = memberRepository.findById(currentMemberId)
            .orElseThrow(() -> new MemberExceptions(NOT_FOUND_USER));

        double points=0;
        try {
            points = Integer.parseInt(requestDto.getPoints());
            if(points < 0 ) {
                throw new MemberExceptions(INVALID_POINT_VALUE);
            }
        }
        catch (NumberFormatException e) {
            throw new MemberExceptions(INVALID_POINT_VALUE);
        }

        member.addPoints(points);
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }
}
