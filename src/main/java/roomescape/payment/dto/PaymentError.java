package roomescape.payment.dto;

public record PaymentError(
        String code,
        String message
) {

    public static PaymentError connectFail(){
        return new PaymentError("500", "일시적으로 연결이 불안정합니다. 잠시후 다시 시도해주세요.");
    }
}
