package com.sparta.outsourcing.domain.member.entity;

public enum MemberRole {
	USER(Authority.USER),  // 사용자 권한
	OWNER(Authority.OWNER);  // 사장님 권한

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
