package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationStatus;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import io.jeeyeon.app.ticketReserve.infra.queueToken.QueueTokenEntity;
import io.jeeyeon.app.ticketReserve.infra.queueToken.QueueTokenJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.reservation.ReservationEntity;
import io.jeeyeon.app.ticketReserve.infra.reservation.ReservationJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatEntity;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.user.UserEntity;
import io.jeeyeon.app.ticketReserve.infra.user.UserJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
class PaymentFacadeTest {

    @Autowired
    PaymentFacade paymentFacade;

    @Autowired
    ReservationJpaRepository reservationJpaRepository;

    @Autowired
    QueueTokenJpaRepository queueTokenJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @BeforeEach
    void init() {
        Long reservationId = 1L;
        Long seatId = 1L;
        Long tokenId = 1L;
        Long userId = 1L;
        Long concertId = 1L;

        String seatNumber = "G01";

        //예약 정보 만들기
        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setReservationId(reservationId);
        reservationEntity.setSeatId(seatId);
        reservationEntity.setTokenId(tokenId);
        reservationEntity.setStatus(ReservationStatus.RESERVED);
        reservationJpaRepository.save(reservationEntity);

        //토큰 정보 만들기
        QueueTokenEntity queueTokenEntity = new QueueTokenEntity();
        queueTokenEntity.setTokenId(tokenId);
        queueTokenEntity.setUserId(userId);
        queueTokenEntity.setConcertId(concertId);
        queueTokenJpaRepository.save(queueTokenEntity);

        //좌석 정보 만들기
        SeatEntity seatEntity = new SeatEntity();
        seatEntity.setSeatId(seatId);
        seatEntity.setSeatNumber(seatNumber);
        seatEntity.setStatus(SeatStatus.RESERVED);
        seatEntity.setTicketPrice(500);
        seatJpaRepository.save(seatEntity);
    }

    @Test
    @DisplayName("결제")
    @Sql("/member.sql")
    void payTest() throws Exception {
        //given
        Long reservationId = 1L;
        Long userId = 1L;
        Long seatId = 1L;

        //when
        paymentFacade.processPayment(reservationId, userId);

        //then
        Optional<UserEntity> findUser = userJpaRepository.findById(userId);
        Assertions.assertEquals(500,findUser.get().getBalance());

        Optional<SeatEntity> findSeat = seatJpaRepository.findById(seatId);
        Assertions.assertEquals(SeatStatus.PAID,findSeat.get().getStatus());

        Optional<ReservationEntity> findReservation = reservationJpaRepository.findById(reservationId);
        Assertions.assertEquals(ReservationStatus.CONFIRMED,findReservation.get().getStatus());
    }

}