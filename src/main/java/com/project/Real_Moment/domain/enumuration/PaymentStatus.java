package com.project.Real_Moment.domain.enumuration;

public enum PaymentStatus {

    PAYMENT_READY("결제준비"),
    PAYMENT_DONE("결제완료"),
    DELIVERY_READY("배송준비"),
    DELIVERY_DOING("배송중"),
    DELIVERY_DONE("배송완료"),
    CANCEL("결제취소"),
    REFUND_REQUEST("환불요청"),
    REFUND_DONE("환불완료"),
    DONE("구매확정");

    // 문자열을 저장할 필드
    private final String paymentStatus;

    // 생성자
    private PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Getter
    public String getPaymentStatus() {
        return paymentStatus;
    }

    // 문자열을 받아와 알맞는 상수 반환
    public static PaymentStatus getStatus(String text) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.getPaymentStatus().equalsIgnoreCase(text)) {
                return status;
            }
        }
        throw new IllegalArgumentException("해당하는 문자열을 찾을 수 없습니다. : " + text);
    }

    // 상수 문자열을 받아 알맞는 문자열로 변환
    public static String getDescription(String text) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equalsIgnoreCase(text)) {
                return status.getPaymentStatus();
            }
        }
        throw new IllegalArgumentException("해당하는 Enum 상수를 찾을 수 없습니다. : " + text);
    }
}
