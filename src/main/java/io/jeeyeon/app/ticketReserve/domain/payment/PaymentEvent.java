package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentEvent {
    private Long paymentId;
    private Long reservationId;
    private Long userId;
    private Integer amount;
    private LocalDateTime timestamp;

    public PaymentEvent(Long paymentId, Long reservationId, Long userId, Integer amount, LocalDateTime timestamp) {
        this.paymentId = paymentId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}


