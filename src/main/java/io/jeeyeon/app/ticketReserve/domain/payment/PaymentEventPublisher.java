package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(PaymentEvent paymentEvent) {
        applicationEventPublisher.publishEvent(paymentEvent);
    }
}