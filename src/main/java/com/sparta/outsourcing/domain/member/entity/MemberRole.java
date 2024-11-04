package com.sparta.outsourcing.domain.member.entity;

public enum MemberRole {
	USER(Authority.USER),
	OWNER(Authority.OWNER);

	private final String authority;

	MemberRole(String authority) {
		this.authority = authority;
	}

	public String getRole() {
		return this.authority;
	}

	public static class Authority {
		public static final String USER = "ROLE_USER";
		public static final String OWNER = "ROLE_OWNER";
	}
}
