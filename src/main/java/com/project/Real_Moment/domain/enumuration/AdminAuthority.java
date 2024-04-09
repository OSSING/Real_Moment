package com.project.Real_Moment.domain.enumuration;

public enum AdminAuthority {
    ROLE_ADMIN("관리자"),
    ROLE_CUSTOMER("고객관리"),
    ROLE_OPERATOR("운영관리"),
    ROLE_REPRESENTATIVE("대표");

    /**
     * ---대표---
     *
     * 회원가입
     * 관리자 관리
     * 회원 등급 관리
     * 관리자 조회
     *
     * ---운영 관리---
     *
     * 주문 상태 관리
     * 상품 관리
     * 카테고리 관리
     *
     * ---고객 관리---
     *
     * 후기 관리
     * Q&A 관리
     * 1:1 문의 관리
     * 공지 사항 관리
     *
     * ---공통---
     *
     * 주문 내역 조회
     * 공지 사항 조회
     * 후기 목록 조회
     * Q&A 목록 조회
     * 1:1 문의 목록 조회
     * 상품 목록 조회
     * 카테고리 목록 조회
     * 회원 목록 조회
     * 회원 등급 조회
     */

    private final String adminAuthority;

    private AdminAuthority(String adminAuthority) {
        this.adminAuthority = adminAuthority;
    }

    public String getAdminAuthority() {
        return adminAuthority;
    }

    // 문자열을 받아와 알맞는 상수 반환
    public static AdminAuthority getAuthority(String text) {
        for (AdminAuthority authority : AdminAuthority.values()) {
            if (authority.getAdminAuthority().equalsIgnoreCase(text)) {
                return authority;
            }
        }

        throw new IllegalArgumentException("해당하는 문자열을 찾을 수 없습니다. : " + text);
    }

    // 상수 문자열을 받아와 알맞는 문자열로 반환
    public static String getDescription(String text) {
        for (AdminAuthority authority : AdminAuthority.values()) {
            if (authority.name().equalsIgnoreCase(text)) {
                return authority.getAdminAuthority();
            }
        }

        throw new IllegalArgumentException("해당하는 Enum 상수를 찾을 수 없습니다. : " + text);
    }
}
