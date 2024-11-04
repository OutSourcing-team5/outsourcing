package com.sparta.outsourcing.domain.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.common.JwtUtil;
import com.sparta.outsourcing.common.PasswordEncoder;
import com.sparta.outsourcing.domain.member.dto.DeleteMemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;
import com.sparta.outsourcing.domain.store.entity.Store;
import com.sparta.outsourcing.domain.store.repository.StoreRepository;

import jakarta.servlet.http.HttpServletResponse;
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
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        // 비밀번호 검증
        if (!PasswordEncoder.verifyPassword(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호의 형식이 올바르지 않습니다.");
        }
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        // 사용자 등록
        Member member = Member.createOf(requestDto.getUserName(), encodedPassword, requestDto.getEmail(), requestDto.getAddress(), requestDto.getRole());
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    @Transactional
	public void login(MemberRequestDto requestDto, HttpServletResponse response) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
            new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        if (!this.passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        String token = jwtUtil.createToken(member.getEmail());
        jwtUtil.addJwtToCookie(token, response);
	}

	public void deleteMember(DeleteMemberRequestDto requestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(
            () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (member.isDeleted()) {
            throw new IllegalArgumentException("이미 삭제된 회원입니다.");
        }

        member.delete();

        List<Store> stores = storeRepository.findAllByActiveMember(member);
        stores.forEach(store -> {
            List<Menu> menus = menuRepository.findAllByActiveStore(store);
            menus.forEach(Menu::delete);
            menuRepository.saveAll(menus);
            store.delete();
            }
        );
        storeRepository.saveAll(stores);

        List<Order> orders = orderRepository.findAllByActiveMember(member);
        orders.forEach(Order::delete);
        orderRepository.saveAll(orders);
	}
}
