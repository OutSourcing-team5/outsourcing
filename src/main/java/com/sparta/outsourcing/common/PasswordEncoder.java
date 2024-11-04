package com.sparta.outsourcing.common;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class PasswordEncoder {
	public static boolean verifyPassword(String password) {
		return password.matches("((?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})");
	}

	public String encode(String password) {
		return BCrypt.withDefaults().hashToString(4, password.toCharArray());
	}
}