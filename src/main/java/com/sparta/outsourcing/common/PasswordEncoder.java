package com.sparta.outsourcing.common;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class PasswordEncoder {
	public String encode(String password) {
		return BCrypt.withDefaults().hashToString(4, password.toCharArray());
	}

	public boolean matches(String rawPassword, String encodePassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodePassword);
		return result.verified;
	}
}
