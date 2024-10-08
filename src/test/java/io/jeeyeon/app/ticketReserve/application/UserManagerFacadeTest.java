package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class UserManagerFacadeTest {

    @Autowired
    UserManagerFacade userManagerFacade;

    @Test
    @DisplayName("잔액 충전")
    @Sql("/data-test.sql")
    void test(){
        //given
        Long userId = 1L;
        Integer amount = 1000;
        //when
        User user = userManagerFacade.chargeBalancePessimistic(userId, amount);

        //then
        Assertions.assertEquals(2000,user.getBalance());
    }

    @Test
    @DisplayName("잔액 조회")
    @Sql("/data-test.sql")
    void test2(){
        //given
        Long userId = 3L;
        //when
        Integer balance = userManagerFacade.checkBalance(userId);

        //then
        Assertions.assertEquals(3000,balance);
    }




}