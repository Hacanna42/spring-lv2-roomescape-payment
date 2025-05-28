package roomescape.common.dto;

public record PaymentError(
        String code,
        String message
) {
    public String formatMessage() {
        return code + " : " + message;
    }
}
