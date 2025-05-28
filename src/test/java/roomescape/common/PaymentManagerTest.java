package roomescape.common;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import roomescape.common.dto.PaymentRequest;
import roomescape.exception.custom.reason.payment.PaymentConfirmException;

@RestClientTest
class PaymentManagerTest {

    private final PaymentManager paymentManager;

    private final RestClient.Builder restClientBuilder;
    private final MockServerRestClientCustomizer customizer;

    @Autowired
    public PaymentManagerTest(
            final RestClient.Builder restClientBuilder,
            final MockServerRestClientCustomizer customizer
    ) {
        this.restClientBuilder = restClientBuilder;
        this.customizer = customizer;
        customizer.customize(restClientBuilder);
        this.paymentManager = new PaymentManager(restClientBuilder, new ObjectMapper(), "test");
    }

    @DisplayName("정상적으로 호출에 성공하면 예외가 발생하지 않는다.")
    @Test
    void confirmPayment() {
        // given
        String response = "";

        customizer.getServer(restClientBuilder)
                .expect(requestTo("https://api.tosspayments.com/v1/payments/confirm"))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        final PaymentRequest request = new PaymentRequest("orderId", "paymentKey", 1000L, "paymentType");

        // when & then
        assertThatCode(() -> {
            paymentManager.confirmPayment(request);
        }).doesNotThrowAnyException();
    }

    @DisplayName("toss api에서 400 응답시 exception이 발생한다.")
    @Test
    void confirmPayment1() {
        // given
        final String response = """
                {
                  "code": "PAYMENT_DENIED",
                  "message": "결제가 거부되었습니다."
                }
                """;

        customizer.getServer(restClientBuilder)
                .expect(requestTo("https://api.tosspayments.com/v1/payments/confirm"))
                .andRespond(withStatus(HttpStatusCode.valueOf(400))
                        .contentType(MediaType.APPLICATION_JSON).body(response));

        final PaymentRequest request = new PaymentRequest("orderId", "paymentKey", 1000L, "paymentType");

        // when & then
        assertThatThrownBy(() -> {
            paymentManager.confirmPayment(request);
        }).isInstanceOf(PaymentConfirmException.class);
    }

    @DisplayName("toss api에서 500 응답시 exception이 발생한다.")
    @Test
    void confirmPayment2() {
        // given
        final String response = """
                {
                  "code": "TOSS_SERVER_INTERNAL_ERROR",
                  "message": "서버가 문제가 발생했습니다."
                }
                """;

        customizer.getServer(restClientBuilder)
                .expect(requestTo("https://api.tosspayments.com/v1/payments/confirm"))
                .andRespond(withStatus(HttpStatusCode.valueOf(500))
                        .contentType(MediaType.APPLICATION_JSON).body(response));

        final PaymentRequest request = new PaymentRequest("orderId", "paymentKey", 1000L, "paymentType");

        // when & then
        assertThatThrownBy(() -> {
            paymentManager.confirmPayment(request);
        }).isInstanceOf(PaymentConfirmException.class);
    }
}
