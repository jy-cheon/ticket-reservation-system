package io.jeeyeon.app.ticketReserve.interfaces.event;

import io.jeeyeon.app.ticketReserve.domain.payment.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.send.DataPlatformSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PaymentEventListener {

    private final DataPlatformSendService sendService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handlePaymentEvent(PaymentEvent paymentEvent) {
        // 성공한 결제 정보 전송
        sendService.sendPaymentInfo(paymentEvent);
    }

}
