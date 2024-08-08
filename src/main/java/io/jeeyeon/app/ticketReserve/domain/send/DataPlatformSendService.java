package io.jeeyeon.app.ticketReserve.domain.send;

import io.jeeyeon.app.ticketReserve.domain.payment.PaymentEvent;
import org.springframework.stereotype.Service;

@Service
public class DataPlatformSendService {

    public void sendPaymentInfo(PaymentEvent paymentEvent) {
        // 결제 정보 전송 로직 구현
        System.out.println("Sending payment info: " + paymentEvent);
        // 예: 외부 API 호출, 메일 발송 등
    }
}