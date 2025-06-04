package roomescape.reservation.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import roomescape.payment.dto.PaymentRequest;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CompletedPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Reservation reservation;

    private String paymentKey;

    private Long amount;

    protected CompletedPayment() {
        this.id = null;
    }

    public static CompletedPayment of(Reservation reservation, PaymentRequest paymentRequest) {
        return new CompletedPayment(
                null,
                reservation,
                paymentRequest.paymentKey(),
                paymentRequest.amount()
        );
    }
}
