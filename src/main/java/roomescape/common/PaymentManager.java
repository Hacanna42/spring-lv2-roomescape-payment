package roomescape.common;

import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import roomescape.common.dto.PaymentError;
import roomescape.common.dto.PaymentRequest;
import roomescape.exception.custom.reason.payment.PaymentConfirmException;

// toss pg사 이용
@Component
public class PaymentManager {

    private final RestClient restClient;

    public PaymentManager() {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.tosspayments.com")
                .defaultHeader("Authorization", "Basic dGVzdF9nc2tfZG9jc19PYVB6OEw1S2RtUVhrelJ6M3k0N0JNdzY6")
                .build();
    }

    public void confirmPayment(final PaymentRequest paymentRequest) {
        restClient.post()
                .uri(URI.create("/v1/payments/confirm"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    final String responseBody = new String(response.getBody().readAllBytes());
                    final PaymentError error = new ObjectMapper().readValue(responseBody, PaymentError.class);
                    throw new PaymentConfirmException(error);
                }))
                .toBodilessEntity();
    }
}
