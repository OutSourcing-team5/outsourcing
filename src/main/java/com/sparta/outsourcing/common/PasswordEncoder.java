package com.sparta.outsourcing.common;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
	public static boolean verifyPassword(String password) {
		return password.matches("((?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})");
	}
}
