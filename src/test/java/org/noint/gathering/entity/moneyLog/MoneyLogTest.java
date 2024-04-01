package org.noint.gathering.entity.moneyLog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.noint.gathering.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.noint.gathering.entity.moneyLog.QMoneyLog.moneyLog;

@SpringBootTest
@Transactional
class MoneyLogTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;


    @Test
    public void 머니로그_엔티티_충전_테스트() throws Exception {
        Member user1 = new Member("a@b.c", "유저1", "password");
        em.persist(user1);
        MoneyLog chargeLog = new Charge(BigDecimal.valueOf(20000), BigDecimal.valueOf(20000), user1, "충전_id");
        em.persist(chargeLog);
        em.flush();
        em.clear();

        List<MoneyLog> moneyLogs = queryFactory
                .selectFrom(moneyLog)
                .fetch();

        assertThat(moneyLogs.size()).isEqualTo(1);
        assertThat(moneyLogs.getFirst().getDtype()).isEqualTo("Charge");
    }

    @Test
    public void 머니로그_엔티티_예약_테스트() throws Exception {
        Member user1 = new Member("a@b.c", "유저1", "password");
        em.persist(user1);
        Room diamondRoom = new Room("다이아 룸", 10, BigDecimal.valueOf(20000));
        em.persist(diamondRoom);
        TimeSlot time = new TimeSlot("10:00 ~ 11:50");
        em.persist(time);
        RoomSchedule newSchedule = new RoomSchedule(LocalDate.now(), diamondRoom, time);
        em.persist(newSchedule);
        Gathering newGathering = new Gathering("모각코", "각자 모여 코딩합시다", 4, user1);
        em.persist(newGathering);
        Reservation newReservation = new Reservation(UUID.randomUUID().toString(), newGathering, newSchedule);
        em.persist(newReservation);

        MoneyLog reservationLog = new Reservations(BigDecimal.valueOf(10000), BigDecimal.valueOf(10000), user1, newReservation);
        em.persist(reservationLog);
        em.flush();
        em.clear();

        List<MoneyLog> moneyLogs = queryFactory
                .selectFrom(moneyLog)
                .fetch();

        assertThat(moneyLogs.size()).isEqualTo(1);
        assertThat(moneyLogs.getFirst().getDtype()).isEqualTo("Reservations");
    }

    @Test
    public void 머니로그_엔티티_송입금_테스트() throws Exception {
        Member user1 = new Member("a@b.c", "유저1", "password");
        Member user2 = new Member("aa@b.c", "유저2", "password");
        em.persist(user1);
        em.persist(user2);
        MoneyLog depositLog = new Deposit(BigDecimal.valueOf(10000), BigDecimal.valueOf(20000), user1, user2);
        em.persist(depositLog);
        MoneyLog transferLog = new Transfer(BigDecimal.valueOf(10000), BigDecimal.ZERO, user2, user1);
        em.persist(transferLog);

        em.flush();
        em.clear();

        List<MoneyLog> moneyLogs = queryFactory
                .selectFrom(moneyLog)
                .fetch();

        assertThat(moneyLogs.size()).isEqualTo(2);
        assertThat(moneyLogs).extracting("dtype").containsExactly("Deposit", "Transfer");
    }
}