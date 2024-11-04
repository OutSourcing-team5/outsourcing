package com.sparta.outsourcing.common;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {

	// Header KEY 값
	public static final String AUTHORIZATION_HEADER = "Authorization";
	// 사용자 권한 값의 KEY
	public static final String AUTHORIZATION_KEY = "auth";
	// Token 식별자
	public static final String BEARER_PREFIX = "Bearer ";
	// 토큰 만료시간
	private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

	@Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
	private String secretKey;
	private Key key;
	private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	// 로그 설정
	public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

	@PostConstruct
	public void init() {
		byte[] bytes = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(bytes);
	}

	//토큰 생성
	public String createToken(String email) {
		Date date = new Date();
		String var10000 = Jwts.builder()
			.setSubject(email)
			.setExpiration(new Date(date.getTime() + 3600000L))
			.setIssuedAt(date)
			.signWith(this.key, this.signatureAlgorithm)
			.compact();
		return "Bearer " + var10000;
	}
}
