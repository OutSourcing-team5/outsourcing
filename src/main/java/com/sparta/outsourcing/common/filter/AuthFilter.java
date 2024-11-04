package com.sparta.outsourcing.common.filter;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.sparta.outsourcing.common.JwtUtil;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;


@Slf4j(topic = "AuthFilter")
@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {

	private final MemberRepository memberRepository;
	private final JwtUtil jwtUtil;

	@Generated
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String url = httpServletRequest.getRequestURI();

		if (StringUtils.hasText(url) && (url.startsWith("/api/auth"))
		) {
			// 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
			chain.doFilter(request, response); // 다음 Filter 로 이동
		} else {
			// 나머지 API 요청은 인증 처리 진행
			// 토큰 확인
			String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);
			if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
				// JWT 토큰 substring
				String token = jwtUtil.substringToken(tokenValue);
				// 토큰 검증
				if (!jwtUtil.validateToken(token)) {
					throw new IllegalArgumentException("Token Error");
				}
				// 토큰에서 사용자 정보 가져오기(진짜 유저정보를 가져옴)
				Claims info = jwtUtil.getUserInfoFromToken(token);
				Member member = memberRepository.findByEmail(info.getSubject()).orElseThrow(() ->
					new NullPointerException("Not Found Member")
				);

				request.setAttribute("id", member.getId());
				request.setAttribute("email", member.getEmail());
				request.setAttribute("role", member.getRole());
				chain.doFilter(request, response); // 다음 Filter 로 이동
			} else {
				throw new IllegalArgumentException("Not Found Token");
			}
		}
	}
}
