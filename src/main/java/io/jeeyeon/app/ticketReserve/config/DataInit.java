package io.jeeyeon.app.ticketReserve.config;


import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertEntity;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertScheduleEntity;
import io.jeeyeon.app.ticketReserve.infra.concert.ConcertScheduleJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatEntity;
import io.jeeyeon.app.ticketReserve.infra.seat.SeatJpaRepository;
import io.jeeyeon.app.ticketReserve.infra.user.UserEntity;
import io.jeeyeon.app.ticketReserve.infra.user.UserJpaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Component
public class DataInit {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ConcertJpaRepository concertJpaRepository;

    @Autowired
    ConcertScheduleJpaRepository concertScheduleJpaRepository;

    @Autowired
    SeatJpaRepository seatJpaRepository;

    @PostConstruct
    void init(){
        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId(1L);
//        userEntity.setBalance(5000);
//        userJpaRepository.save(userEntity);
//
//        userEntity.setUserId(2L);
//        userEntity.setBalance(3000);
//        userJpaRepository.save(userEntity);
//
//        userEntity.setUserId(3L);
//        userEntity.setBalance(1000);
//        userJpaRepository.save(userEntity);

        for (int i = 0; i < 100 ; i++) {
            userEntity.setUserId((long) i);
            userEntity.setBalance(5000);
            userJpaRepository.save(userEntity);
        }


        ConcertEntity concertEntity1 = new ConcertEntity();
        concertEntity1.setConcertName("아이유 콘서트");
        ConcertEntity concertEntity2 = new ConcertEntity();
        concertEntity2.setConcertName("뉴진스 콘서트");
        ConcertEntity concertEntity3 = new ConcertEntity();
        concertEntity3.setConcertName("레드벨벳 콘서트");

        concertJpaRepository.save(concertEntity1);
        concertJpaRepository.save(concertEntity2);
        concertJpaRepository.save(concertEntity3);

        ConcertScheduleEntity schedule1 = new ConcertScheduleEntity();
        schedule1.setConcertDate(LocalDateTime.parse("2024-08-29T18:00:00"));
        schedule1.setConcertId(1l);
        schedule1.setLocation("종합운동장");
        ConcertScheduleEntity schedule2 = new ConcertScheduleEntity();
        schedule2.setConcertDate(LocalDateTime.parse("2024-09-29T18:00:00"));
        schedule2.setConcertId(2l);
        schedule2.setLocation("대만");
        ConcertScheduleEntity schedule3 = new ConcertScheduleEntity();
        schedule3.setConcertDate(LocalDateTime.parse("2024-10-29T19:00:00"));
        schedule3.setConcertId(2l);
        schedule3.setLocation("뉴욕시티");

        concertScheduleJpaRepository.save(schedule1);
        concertScheduleJpaRepository.save(schedule2);
        concertScheduleJpaRepository.save(schedule3);

        List<SeatEntity> seats = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            SeatEntity seat = new SeatEntity();
            seat.setConcertScheduleId(1L);
            seat.setStatus(SeatStatus.AVAILABLE);
            seat.setSeatNumber("s" + i);
            seat.setTicketPrice(10000);
            seats.add(seat);
        }

        seatJpaRepository.saveAll(seats);

    }

}
