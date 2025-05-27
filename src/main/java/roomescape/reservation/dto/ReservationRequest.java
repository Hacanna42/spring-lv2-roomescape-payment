package roomescape.reservation.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ReservationRequest(
        @NotNull LocalDate date,
        @NotNull Long timeId,
        @NotNull Long themeId,
        @NotNull String orderId,
        @NotNull String paymentKey,
        @NotNull Long amount,
        @NotNull String paymentType
) {
}
