package com.sparta.outsourcing.common;

import org.springframework.stereotype.Component;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Component
public class PasswordEncoder {
	public static boolean verifyPassword(String password) {
		return password.matches("((?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9])(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=].{8,})");
	}

	public String encode(String password) {
		return BCrypt.withDefaults().hashToString(4, password.toCharArray());
	}

	public boolean matches(String rawPassword, String encodePassword) {
		BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodePassword);
		return result.verified;
	}

	// public boolean equals(String newPassword, String passwordConfirm) {
	// 	BCrypt.Result result = BCrypt.verifyer().verify(newPassword.toCharArray(), passwordConfirm);
	// 	return result.verified;
	// }
}
