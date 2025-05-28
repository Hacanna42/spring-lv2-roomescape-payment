package roomescape.common;

import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
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

    private final ObjectMapper objectMapper;
    private final RestClient restClient;

    @Value("${secret.payment-key}")
    private String authorizationKey;

    public PaymentManager(
            final ObjectMapper objectMapper
    ) {
        this.restClient = RestClient.builder()
                .baseUrl("https://api.tosspayments.com")
                .defaultHeader("Authorization", authorizationKey)
                .build();
        this.objectMapper = objectMapper;
    }

    public void confirmPayment(final PaymentRequest paymentRequest) {
        restClient.post()
                .uri(URI.create("/v1/payments/confirm"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    final String responseBody = new String(response.getBody().readAllBytes());
                    final PaymentError error = objectMapper.readValue(responseBody, PaymentError.class);
                    throw new PaymentConfirmException(error);
                }))
                .toBodilessEntity();
    }
}
