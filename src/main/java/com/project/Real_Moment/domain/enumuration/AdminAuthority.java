package com.project.Real_Moment.domain.enumuration;

public enum AdminAuthority {
    ROLE_CUSTOMER_MANAGER("고객관리"),
    ROLE_PRODUCT_MANAGER("상품관리"),
    ROLE_ORDER_MANAGER("주문관리"),
    ROLE_ADMIN_SUPERVISOR("총관리자");

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
