package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record ReservationPaymentRequest(
        @JsonUnwrapped @Valid ReservationRequest reservationRequest,
        @JsonUnwrapped @Valid PaymentRequest paymentRequest
) {
}
